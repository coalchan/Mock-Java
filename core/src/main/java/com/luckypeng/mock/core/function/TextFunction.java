package com.luckypeng.mock.core.function;

import com.luckypeng.mock.core.function.BasicFunction.CharacterEnum;

import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.function.schema.Function;
import org.apache.commons.lang3.StringUtils;

/**
 * @author coalchan
 * @date 2019/4/4
 */
@Functions
public class TextFunction {
    private TextFunction() {}

    private static final int DEFAULT_TITLE_MIN = 3;
    private static final int DEFAULT_TITLE_MAX = 7;
    private static final int DEFAULT_WORD_MIN = 3;
    private static final int DEFAULT_WORD_MAX = 10;
    private static final int DEFAULT_SENTENCE_MIN = 12;
    private static final int DEFAULT_SENTENCE_MAX = 18;
    private static final int DEFAULT_PARAGRAPH_MIN = 3;
    private static final int DEFAULT_PARAGRAPH_MAX = 7;

    private static final String DICT_COMMON_CN =
            StringUtils.deleteWhitespace("的一是在不了有和人这中大为上个国我以要他时来用们生到作地于出就分对成会可主" +
                    "发年动同工也能下过子说产种面而方后多定行学法所民得经十三之进着等部度家电力里如水化高自二理起小物现实加量" +
                    "都两体制机当使点从业本去把性好应开它合还因由其些然前外天政四日那社义事平形相全表间样与关各重新线内数正心" +
                    "反你明看原又么利比或但质气第向道命此变条只没结解问意建月公无系军很情者最立代想已通并提直题党程展五果料象" +
                    "员革位入常文总次品式活设及管特件长求老头基资边流路级少图山统接知较将组见计别她手角期根论运农指几九区强放" +
                    "决西被干做必战先回则任取据处队南给色光门即保治北造百规热领七海口东导器压志世金增争济阶油思术极交受联什认" +
                    "六共权收证改清己美再采转更单风切打白教速花带安场身车例真务具万每目至达走积示议声报斗完类八离华名确才科张" +
                    "信马节话米整空元况今集温传土许步群广石记需段研界拉林律叫且究观越织装影算低持音众书布复容儿须际商非验连断" +
                    "深难近矿千周委素技备半办青省列习响约支般史感劳便团往酸历市克何除消构府称太准精值号率族维划选标写存候毛亲" +
                    "快效斯院查江型眼王按格养易置派层片始却专状育厂京识适属圆包火住调满县局照参红细引听该铁价严龙飞");

    @Function
    public static String cparagraph() {
        return cparagraph(DEFAULT_PARAGRAPH_MIN, DEFAULT_PARAGRAPH_MAX);
    }

    @Function
    public static String cparagraph(int min, int max) {
        return cparagraph((int) BasicFunction.integer(min, max));
    }

    @Function
    public static String cparagraph(int length) {
        String[] sentences = new String[length];
        for (int i = 0; i < length; i++) {
            sentences[i] = csentence();
        }
        return String.join("", sentences);
    }

    @Function
    public static String csentence() {
        return csentence(DEFAULT_SENTENCE_MIN, DEFAULT_SENTENCE_MAX);
    }

    @Function
    public static String csentence(int min, int max) {
        return csentence((int) BasicFunction.integer(min, max));
    }

    @Function
    public static String csentence(int length) {
        String[] words = new String[length];
        for (int i = 0; i < length; i++) {
            words[i] = cword();
        }
        return String.join("", words) + "。";
    }

    @Function
    public static String ctitle() {
        return ctitle((int) BasicFunction.integer(DEFAULT_TITLE_MIN, DEFAULT_TITLE_MAX));
    }

    @Function
    public static String ctitle(int min, int max) {
        return ctitle((int) BasicFunction.integer(min, max));
    }

    @Function
    public static String ctitle(int length) {
        String[] words = new String[length];
        for (int i = 0; i < length; i++) {
            words[i] = cword();
        }
        return String.join("", words);
    }

    @Function
    public static String cword() {
        return cword(DICT_COMMON_CN);
    }

    @Function
    public static String cword(String pool) {
        return cword(pool, 1);
    }

    @Function
    public static String cword(int length) {
        return cword(DICT_COMMON_CN, length);
    }

    @Function
    public static String cword(int min, int max) {
        return cword(DICT_COMMON_CN, min, max);
    }

    @Function
    public static String cword(String pool, int min, int max) {
        return cword(pool, BasicFunction.integer(min, max));
    }

    @Function
    public static String cword(String pool, long length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += pool.charAt((int) BasicFunction.integer(0, pool.length() - 1));
        }
        return result;
    }

    @Function
    public static String paragraph() {
        return paragraph(DEFAULT_PARAGRAPH_MIN, DEFAULT_PARAGRAPH_MAX);
    }

    @Function
    public static String paragraph(int min, int max) {
        return paragraph((int) BasicFunction.integer(min, max));
    }

    @Function
    public static String paragraph(int length) {
        String[] sentences = new String[length];
        for (int i = 0; i < length; i++) {
            sentences[i] = sentence();
        }
        return String.join(" ", sentences);
    }

    @Function
    public static String sentence() {
        return sentence(DEFAULT_SENTENCE_MIN, DEFAULT_SENTENCE_MAX);
    }

    @Function
    public static String sentence(int min, int max) {
        return sentence((int) BasicFunction.integer(min, max));
    }

    @Function
    public static String sentence(int length) {
        String[] words = new String[length];
        for (int i = 0; i < length; i++) {
            words[i] = word();
        }
        return StringUtils.capitalize(String.join(" ", words)) + ".";
    }

    @Function
    public static String title() {
        return title((int) BasicFunction.integer(DEFAULT_TITLE_MIN, DEFAULT_TITLE_MAX));
    }

    @Function
    public static String title(int min, int max) {
        return title((int) BasicFunction.integer(min, max));
    }

    @Function
    public static String title(int length) {
        String[] words = new String[length];
        for (int i = 0; i < length; i++) {
            words[i] = StringUtils.capitalize(word());
        }
        return String.join(" ", words);
    }

    @Function
    public static String word() {
        return BasicFunction.string(CharacterEnum.lower, DEFAULT_WORD_MIN, DEFAULT_WORD_MAX);
    }

    @Function
    public static String word(int length) {
        return BasicFunction.string(CharacterEnum.lower, length);
    }

    @Function
    public static String word(int min, int max) {
        return BasicFunction.string(CharacterEnum.lower, min, max);
    }
}
