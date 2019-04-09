package com.luckypeng.mock.core.function;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.luckypeng.mock.core.function.AddressFunction.*;

public class AddressFunctionTest {

    @Test
    public void testRegion() {
        assertThat(region(), allOf(isIn(REGION)));
    }

    @Test
    public void testProvince() {
        List<String> provinces = CHINA.stream().map(Province::getName).collect(Collectors.toList());
        for (int i = 0; i < 100; i++) {
            assertThat(province(), allOf(isIn(provinces)));
        }
    }

    @Test
    public void testCity() {
        List<String> cities = CHINA.stream()
                .flatMap(province -> province.getCity().stream()).map(City::getName).collect(Collectors.toList());
        for (int i = 0; i < 100; i++) {
            assertThat(city(), allOf(isIn(cities)));
        }
    }

    @Test
    public void testCounty() {
        List<String> counties = CHINA.stream()
                .flatMap(province -> province.getCity().stream())
                .flatMap(city -> city.getArea().stream()).collect(Collectors.toList());
        for (int i = 0; i < 100; i++) {
            assertThat(county(), allOf(isIn(counties)));
        }
    }

    @Test
    public void testZip() {
        assertEquals(zip().length(), 6);
    }
}