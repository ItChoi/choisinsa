package com.mall.choisinsa.config;

import com.mall.choisinsa.filter.PermitAllFilter;
import com.mall.choisinsa.service.SecurityMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity(debug = true)
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityMemberConfig {
    private final AuthenticationProvider authenticationProvider;
    private final SecurityMemberService securityMemberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                /*.authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )*/
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(securityMemberService)
                .authenticationProvider(authenticationProvider);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .and().ignoring().antMatchers(getAnyMatchersForWebSecurity());

        };
    }

    private String[] getAnyMatchersForWebSecurity() {
        return new String[]{
                "/img/**",
                "/h2-console/**",
                "favicon.ico",
        };
    }

}
