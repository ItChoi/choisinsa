package com.mall.choisinsa.web.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class PriceToLocalePrice implements Formatter<Number> {

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(locale);
        // "1,000" -> 1000
        return format.parse(text);
    }

    @Override
    public String print(Number object, Locale locale) {
        // 1000 -> "1,000"
        return NumberFormat.getInstance(locale).format(object);
    }
}
