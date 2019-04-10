package com.luckypeng.mock.core.function;

import com.luckypeng.mock.core.function.BasicFunction.CharacterEnum;
import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.util.ArrayUtils;

/**
 * @author coalchan
 * @since 1.0
 */
@Functions
public class WebFunction {
    private WebFunction() {}

    /**
     * 协议簇
     */
    public static final String[] PROTOCOLS = {
            "http", "ftp", "gopher", "mailto", "mid",
            "cid", "news", "nntp", "prospero", "telnet",
            "rlogin", "tn3270", "wais"
    };

    /**
     * 常见域名后缀
     */
    private static final String[] COMMON_DOMAIN_SUFFIX = {
            "com", "net", "org", "edu", "gov", "int", "mil", "cn"
    };

    /**
     * 国内域名
     */
    private static final String[] INTERNAL_DOMAIN_SUFFIX = {
            "com.cn", "net.cn", "gov.cn", "org.cn"
    };

    /**
     * 中文国内域名
     */
    private static final String[] INTERNAL_CN_DOMAIN_SUFFIX = {
            "中国", "中国互联.公司", "中国互联.网络"
    };

    /**
     * 新国际域名
     */
    private static final String[] NEW_INTERNATIONAL_DOMAIN_SUFFIX = {
            "tel", "biz", "cc", "tv", "info", "name", "hk", "mobi",
            "asia", "cd", "travel", "pro", "museum", "coop", "aero"
    };

    /**
     * 世界各国域名后缀
     */
    private static final String[] OTHER_DOMAIN_SUFFIX = {
            "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao",
            "aq", "ar", "as", "at", "au", "aw", "az", "ba", "bb",
            "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn",
            "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca",
            "cc", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn",
            "co", "cq", "cr", "cu", "cv", "cx", "cy", "cz", "de",
            "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh",
            "es", "et", "ev", "fi", "fj", "fk", "fm", "fo", "fr",
            "ga", "gb", "gd", "ge", "gf", "gh", "gi", "gl", "gm",
            "gn", "gp", "gr", "gt", "gu", "gw", "gy", "hk", "hm",
            "hn", "hr", "ht", "hu", "id", "ie", "il", "in", "io",
            "iq", "ir", "is", "it", "jm", "jo", "jp", "ke", "kg",
            "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz",
            "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu",
            "lv", "ly", "ma", "mc", "md", "mg", "mh", "ml", "mm",
            "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mv", "mw",
            "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni",
            "nl", "no", "np", "nr", "nt", "nu", "nz", "om", "qa",
            "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn",
            "pr", "pt", "pw", "py", "re", "ro", "ru", "rw", "sa",
            "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk",
            "sl", "sm", "sn", "so", "sr", "st", "su", "sy", "sz",
            "tc", "td", "tf", "tg", "th", "tj", "tk", "tm", "tn",
            "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug",
            "uk", "us", "uy", "va", "vc", "ve", "vg", "vn", "vu",
            "wf", "ws", "ye", "yu", "za", "zm", "zr", "zw"
    };

    public static final String[] DOMAIN_SUFFIX = ArrayUtils.concatArrays(
            COMMON_DOMAIN_SUFFIX, INTERNAL_DOMAIN_SUFFIX, INTERNAL_CN_DOMAIN_SUFFIX,
            NEW_INTERNATIONAL_DOMAIN_SUFFIX, OTHER_DOMAIN_SUFFIX
    );

    @Function
    public static final String ip() {
        return BasicFunction.integer(0, 255) + "." +
                BasicFunction.integer(0, 255) + "." +
                BasicFunction.integer(0, 255) + "." +
                BasicFunction.integer(0, 255);
    }

    @Function
    public static final String email() {
        return email(domain());
    }

    @Function
    public static final String email(String domain) {
        return BasicFunction.character(CharacterEnum.lower) + "." + TextFunction.word() + "@" + domain;
    }

    @Function
    public static final String url() {
        return url(protocol());
    }

    @Function
    public static final String url(String protocol) {
        return url(protocol, domain());
    }

    @Function
    public static final String url(String protocol, String host) {
        return protocol + "://" + host + "/" + TextFunction.word();
    }

    @Function
    public static final String protocol() {
        return BasicFunction.pick(PROTOCOLS);
    }

    @Function
    public static final String domain() {
        return domain(tld());
    }

    @Function
    public static final String domain(String tld) {
        return TextFunction.word() + "." + tld;
    }

    @Function
    public static final String tld() {
        return BasicFunction.pick(DOMAIN_SUFFIX);
    }
}
