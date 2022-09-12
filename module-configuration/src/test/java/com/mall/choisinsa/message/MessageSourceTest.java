package com.mall.choisinsa.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void 메시지소스_디폴트_메시지_테스트() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void 메시지소스_없는_값() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void 메시지소스_없는_값_기본_메시지() {
        final String BASIC_MESSAGE = "기본 메시지";
        String result = ms.getMessage("no_code", null, BASIC_MESSAGE, null);
        assertThat(result).isEqualTo(BASIC_MESSAGE);
    }

    @Test
    void 메시지소스_동적_파라미터_메시지() {
        String result = ms.getMessage("hello.name", new Object[]{"itchoi"}, null);
        assertThat(result).isEqualTo("안녕 itchoi");
    }

    @Test
    void 메시지국제화_기본_언어() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void 메시지국제화_영어() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }

}
