package com.luckypeng.mock.core.function;

import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.util.ArrayUtils;

/**
 * @author coalchan
 * @since 1.0
 */
@Functions
public class NameFunction {
    private NameFunction() {}

    /**
     * male name
     */
    private static final String[] FIRST_NAME_MALE = {
            "James", "John", "Robert", "Michael", "William",
            "David", "Richard", "Charles", "Joseph", "Thomas",
            "Christopher", "Daniel", "Paul", "Mark", "Donald",
            "George", "Kenneth", "Steven", "Edward", "Brian",
            "Ronald", "Anthony", "Kevin", "Jason", "Matthew",
            "Gary", "Timothy", "Jose", "Larry", "Jeffrey",
            "Frank", "Scott", "Eric"
    };

    /**
     * female name
     */
    private static final String[] FIRST_NAME_FEMALE = {
            "Mary", "Patricia", "Linda", "Barbara", "Elizabeth",
            "Jennifer", "Maria", "Susan", "Margaret", "Dorothy",
            "Lisa", "Nancy", "Karen", "Betty", "Helen",
            "Sandra", "Donna", "Carol", "Ruth", "Sharon",
            "Michelle", "Laura", "Sarah", "Kimberly", "Deborah",
            "Jessica", "Shirley", "Cynthia", "Angela", "Melissa",
            "Brenda", "Amy", "Anna"
    };

    public static final String[] FIRST_NAMES = ArrayUtils.addAll(FIRST_NAME_MALE, FIRST_NAME_FEMALE);

    public static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown", "Jones",
            "Miller", "Davis", "Garcia", "Rodriguez", "Wilson",
            "Martinez", "Anderson", "Taylor", "Thomas", "Hernandez",
            "Moore", "Martin", "Jackson", "Thompson", "White",
            "Lopez", "Lee", "Gonzalez", "Harris", "Clark",
            "Lewis", "Robinson", "Walker", "Perez", "Hall",
            "Young", "Allen"
    };

    public static final String[] CN_FIRST_NAMES = {
            "王", "李", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴",
            "徐", "孙", "胡", "朱", "高", "林", "何", "郭", "马", "罗",
            "梁", "宋", "郑", "谢", "韩", "唐", "冯", "于", "董", "萧",
            "程", "曹", "袁", "邓", "许", "傅", "沈", "曾", "彭", "吕",
            "苏", "卢", "蒋", "蔡", "贾", "丁", "魏", "薛", "叶", "阎",
            "余", "潘", "杜", "戴", "夏", "锺", "汪", "田", "任", "姜",
            "范", "方", "石", "姚", "谭", "廖", "邹", "熊", "金", "陆",
            "郝", "孔", "白", "崔", "康", "毛", "邱", "秦", "江", "史",
            "顾", "侯", "邵", "孟", "龙", "万", "段", "雷", "钱", "汤",
            "尹", "黎", "易", "常", "武", "乔", "贺", "赖", "龚", "文"
    };

    public static final String[] CN_LAST_NAMES = {
            "伟", "芳", "娜", "秀英", "敏", "静", "丽", "强", "磊", "军",
            "洋", "勇", "艳", "杰", "娟", "涛", "明", "超", "秀兰", "霞",
            "平", "刚", "桂英"
    };

    @Function
    public static String first() {
        return BasicFunction.pick(FIRST_NAMES);
    }

    @Function
    public static String last() {
        return BasicFunction.pick(LAST_NAMES);
    }

    @Function
    public static String name() {
        return name(false);
    }

    @Function
    public static String name(boolean middle) {
        return first() + " " + (middle ? first() + " " : "") + last();
    }

    @Function
    public static String cfirst() {
        return BasicFunction.pick(CN_FIRST_NAMES);
    }

    @Function
    public static String clast() {
        return BasicFunction.pick(CN_LAST_NAMES);
    }

    @Function
    public static String cname() {
        return cfirst() + clast();
    }
}
