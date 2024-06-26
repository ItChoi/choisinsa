//package com.mall.choisinsa.web;
//
//import com.mall.choisinsa.web.filter.LogFilter;
//import com.mall.choisinsa.web.resolver.LoginUserArgResolver;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.servlet.Filter;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    private final LoginUserArgResolver loginUserArgumentResolver;
//
//    @Bean
//    public FilterRegistrationBean logFilter() {
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new LogFilter());
//        filterRegistrationBean.setOrder(1); // 필터 체인 순서
//        filterRegistrationBean.addUrlPatterns("/**"); // 모든 URL에 적용
//
//        return filterRegistrationBean;
//    }
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(loginUserArgumentResolver);
//    }
//}
//
