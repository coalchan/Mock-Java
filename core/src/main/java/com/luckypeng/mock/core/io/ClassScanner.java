package com.luckypeng.mock.core.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author coalchan
 * @date 2019/4/2
 */
@Slf4j
public class ClassScanner {
    private static final String PATH_DELIMITER = "/";
    /**
     * 查询指定包下带有指定注解的所有类
     * @param packageName
     * @param annotationClass
     * @return
     * @throws IOException
     */
    public static List<Class> scan(String packageName, Class annotationClass) {
        List<Class> classes = scan(packageName);
        return classes
                .stream()
                .filter(clazz -> clazz.getAnnotation(annotationClass) != null)
                .collect(Collectors.toList());
    }

    /**
     * 查询指定包下的所有类
     * @param packageName
     * @return
     */
    public static List<Class> scan(String packageName) {
        List<String> classNames = scanClass(packageName);
        List<Class> classes = new ArrayList<>();
        classNames.forEach(className -> {
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("获取指定类失败: " + className, e);
            }
        });
        return classes;
    }

    /**
     * 查找指定包下的所有类名全称
     * @param packageName
     * @return
     */
    public static List<String> scanClass(String packageName) {
        String packagePath = packageToPath(packageName);
        List<String> list = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls;
        try {
            urls = classLoader.getResources(packagePath);
        } catch (IOException e) {
            throw new RuntimeException("查找包下的资源失败: " + packagePath, e);
        }
        while (urls.hasMoreElements()) {
            URL u = urls.nextElement();
            ResourceType type = determineType(u);
            switch (type) {
                case JAR:
                    String path = distillPathFromJarURL(u.getPath());
                    list = scanJar(path, packagePath);
                    break;
                case FILE:
                    String filePath;
                    try {
                        filePath = URLDecoder.decode(u.getPath(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException("URLDecoder.decode URL 失败: ", e);
                    }
                    list = scanFile(filePath, packageName);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    /**
     * 根据URL判断是JAR包还是文件目录
     * @param url
     * @return
     */
    private static ResourceType determineType(URL url) {
        if (url.getProtocol().equals(ResourceType.FILE.getTypeString())) {
            return ResourceType.FILE;
        }
        if (url.getProtocol().equals(ResourceType.JAR.getTypeString())) {
            return ResourceType.JAR;
        }
        throw new IllegalArgumentException("不支持该类型: " + url.getProtocol());
    }

    /**
     * 扫描JAR文件
     * @param path
     * @return
     * @throws IOException
     */
    private static List<String> scanJar(String path, String packagePath) {
        JarFile jar;
        try {
            jar = new JarFile(path);
        } catch (IOException e) {
            throw new RuntimeException("打开JAR文件失败: " + path, e);
        }

        List<String> classNameList = new ArrayList<>(20);

        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            if(name.startsWith(packagePath) && name.endsWith(ResourceType.CLASS_FILE.getTypeString())) {
                name = trimSuffix(name);
                name = pathToPackage(name);

                classNameList.add(name);
            }
        }

        return classNameList;
    }

    /**
     * 扫描文件目录下的类
     * @param path
     * @return
     */
    private static List<String> scanFile(String path, String packageName) {
        File f = new File(path);

        List<String> classNameList = new ArrayList<>(10);

        // 得到目录下所有文件(目录)
        File[] files = f.listFiles();
        if (null != files) {
            int fileLength = files.length;

            for (int ix = 0 ; ix < fileLength ; ++ix) {
                File file = files[ix];

                // 判断是否还是一个目录
                if (file.isDirectory()) {
                    // 递归遍历目录
                    List<String> list =
                            scanFile(file.getAbsolutePath(), concat(packageName, ".", file.getName()));
                    classNameList.addAll(list);

                } else if (file.getName().endsWith(ResourceType.CLASS_FILE.getTypeString())) {
                    // 如果是以.class结尾
                    String className = trimSuffix(file.getName());
                    // 如果类名中有"$"不计算在内
                    if (-1 != className.lastIndexOf("$")) {
                        continue;
                    }

                    // 命中
                    String result = concat(packageName, ".", className);
                    classNameList.add(result);
                }
            }
        } else {
            log.warn(path + " 目录下没有文件！");
        }

        return classNameList;
    }

    /**
     * 把路径字符串转换为包名.
     * a/b/c/d -> a.b.c.d
     *
     * @param path
     * @return
     */
    public static String pathToPackage(String path) {
        if (path.startsWith(PATH_DELIMITER)) {
            path = path.substring(1);
        }
        return path.replaceAll(PATH_DELIMITER, ".");
    }

    /**
     * 包名转换为路径名
     * @param packageName
     * @return
     */
    public static String packageToPath(String packageName) {
        return packageName.replace(".", File.separator);
    }

    /**
     * 将多个对象转换成字符串并连接起来
     * @param objs
     * @return
     */
    public static String concat(Object... objs) {
        StringBuilder sb = new StringBuilder(30);
        for (int ix = 0 ; ix < objs.length ; ++ix) {
            sb.append(objs[ix]);
        }
        return sb.toString();
    }

    /**
     * 去掉文件的后缀名
     * @param name
     * @return
     */
    public static String trimSuffix(String name) {
        int dotIndex = name.indexOf('.');
        if (-1 == dotIndex) {
            return name;
        }

        return name.substring(0, dotIndex);
    }

    public static String distillPathFromJarURL(String url) {
        int startPos = url.indexOf(':');
        int endPos = url.lastIndexOf('!');

        return url.substring(startPos + 1, endPos);
    }
}
