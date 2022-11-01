package com.mall.choisinsa.converter;

import com.mall.choisinsa.web.converter.StringToIntegerConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class StringToIntegerConverterTest {

    @Test
    void stringToInteger() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");

        Assertions.assertThat(result).isEqualTo(10);
    }

    @Test
    void stringToIntegerWithConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());

        Integer result = conversionService.convert("10", Integer.class);
        Assertions.assertThat(result).isEqualTo(10);
    }

    @Test
    void integerToStringWithConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();

        String result = conversionService.convert(10, String.class);
        Assertions.assertThat(result).isEqualTo("10");
    }

    @Test
    void integerToDoubleWithConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();

        Double result = conversionService.convert(10, Double.class);
        Assertions.assertThat(result).isEqualTo(10.0);
    }

    @Test
    void errorDoubleToIntegerWithConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();

        Integer result = conversionService.convert(10.0, Integer.class);
        Assertions.assertThatThrownBy(() -> Assertions.assertThat(result).isEqualTo(10.0));
    }

}