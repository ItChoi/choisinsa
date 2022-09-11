package com.mall.choisinsa.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBean {

    // 스프링 부트를 사용하면 messageSource를 자동으로 빈으로 등록해준다. 따라서 수동 설정 코드가 필요하지 않음
    /*@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "errors");
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }*/
}
