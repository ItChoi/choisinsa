package com.mall.choisinsa.converter;

import com.mall.choisinsa.web.converter.StringToIntegerConverter;
import com.mall.choisinsa.web.formatter.PriceToLocalePrice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        // 컨버터 등록
        conversionService.addConverter(new StringToIntegerConverter());
        // 포매터 등록
        conversionService.addFormatter(new PriceToLocalePrice());

        Integer result1 = conversionService.convert("1000", Integer.class);
        Assertions.assertThat(result1).isEqualTo(1000);

        String result2 = conversionService.convert(1000, String.class);
        Assertions.assertThat(result2).isEqualTo("1,000");

        Long result3 = conversionService.convert("1,000", Long.class);
        Assertions.assertThat(result3).isEqualTo(1000);
    }
}
