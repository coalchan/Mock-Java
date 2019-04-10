package com.luckypeng.mock.core.function;

import com.alibaba.fastjson.JSON;
import com.luckypeng.mock.core.function.BasicFunction.CharacterEnum;
import com.luckypeng.mock.core.function.schema.Function;
import com.luckypeng.mock.core.function.schema.Functions;
import com.luckypeng.mock.core.util.ObjectUtils;
import lombok.Data;

import java.util.List;

/**
 * @author coalchan
 * @since 1.0
 */
@Functions
public class AddressFunction {
    private AddressFunction() {}

    public static String[] REGION = {
            "东北", "华北", "华东", "华中", "华南", "西南", "西北"
    };

    public static List<Province> CHINA = JSON.parseArray(ObjectUtils.fromFile("china.json"), Province.class);

    @Function
    public static String region() {
        return BasicFunction.pick(REGION);
    }

    @Function
    public static String province() {
        return BasicFunction.pick(CHINA).getName();
    }

    @Function
    public static String city() {
        return city(false);
    }

    @Function
    public static String city(boolean prefix) {
        Province province = BasicFunction.pick(CHINA);
        String city = BasicFunction.pick(province.getCity()).getName();
        return prefix ? province.getName() + " " + city : city;
    }

    @Function
    public static String county() {
        return county(false);
    }

    @Function
    public static String county(boolean prefix) {
        Province province = BasicFunction.pick(CHINA);
        City city = BasicFunction.pick(province.getCity());
        String country = BasicFunction.pick(city.getArea());
        return prefix ? province.getName() + " " + city.getName() + " " + country : country;
    }

    @Function
    public static String zip() {
        return BasicFunction.string(CharacterEnum.number, 6);
    }
}

@Data
class Province {
    private String name;
    private List<City> city;
}

@Data
class City {
    private String name;
    private List<String> area;
}