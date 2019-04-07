package com.luckypeng.mock.core.template;

import com.luckypeng.mock.core.function.BasicFunction;
import com.luckypeng.mock.core.util.NumberUtils;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author coalchan
 * @date 2019/4/3
 */
@Data
public class Rule {
    private String key;

    /**
     * 是否有 range
     */
    private boolean range;

    private Long min;

    private Long max;

    private Long count;

    /**
     * 是否有 decimal
     */
    private boolean decimal;

    private Integer dMin;

    private Integer dMax;

    private Integer dCount;

    private Integer step;

    private static final Pattern RE_KEY =
            Pattern.compile("([^\\|]+)\\|?(?:\\+(\\d+)|([\\+-]?\\d+-?[\\+-]?\\d*)?(\\.\\d+-?\\d*)?)");
    private static final Pattern RE_RANGE = Pattern.compile("([\\+-]?\\d+)-?([\\+-]?\\d+)?");
    private static final Pattern RE_PLACEHOLDER = Pattern.compile("@([^@#%&()\\?\\s]+)(\\((.*)\\))");

    /**
     * 解析模板
     * @param template
     * @return
     */
    public static Rule fromTemplate(String template) {
        Rule rule = new Rule();
        Matcher matcher = RE_KEY.matcher(template);

        if (matcher.find()) {
            rule.setKey(matcher.group(1));

            rule.setStep(NumberUtils.parseInt(matcher.group(2)));

            rule.setRange(matcher.group(3) != null);
            if (rule.isRange()) {
                Matcher rangeMatcher = RE_RANGE.matcher(matcher.group(3));
                if (rangeMatcher.find()) {
                    rule.setMin(NumberUtils.parseLong(rangeMatcher.group(1)));
                    rule.setMax(NumberUtils.parseLong(rangeMatcher.group(2)));
                }

                rule.setCount(rule.getMax() == null ? rule.getMin() :
                        BasicFunction.integer(rule.getMin(), rule.getMax()));
            }

            rule.setDecimal(matcher.group(4) != null);
            if (rule.isDecimal()) {
                Matcher rangeMatcher = RE_RANGE.matcher(matcher.group(4));
                if (rangeMatcher.find()) {
                    rule.setDMin(NumberUtils.parseInt(rangeMatcher.group(1)));
                    rule.setDMax(NumberUtils.parseInt(rangeMatcher.group(2)));
                }

                rule.setDCount(rule.getDMax() == null ? rule.getDMin() :
                        (int) BasicFunction.integer(rule.getDMin(), rule.getDMax()));
            }
        }

        return rule;
    }

    /**
     * 是否为占位符
     * @param value
     * @return
     */
    public static boolean isPlaceholder(String value) {
        Matcher matcher = RE_PLACEHOLDER.matcher(value);
        return matcher.matches();
    }
}
