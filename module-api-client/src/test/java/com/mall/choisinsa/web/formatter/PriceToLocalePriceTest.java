package com.mall.choisinsa.web.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

class PriceToLocalePriceTest {

    PriceToLocalePrice formatter = new PriceToLocalePrice();

    @Test
    void parse() throws ParseException {
        Number result = formatter.parse("1,000", Locale.KOREA);
        Assertions.assertThat(result).isEqualTo(1000L);
    }

    @Test
    void print() {
        String result = formatter.print(1000000, Locale.KOREA);
        Assertions.assertThat(result).isEqualTo("1,000,000");
    }
}