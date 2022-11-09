package com.mall.choisinsa.config;

import com.mall.choisinsa.filter.JwtFilter;
import com.mall.choisinsa.provider.JwtTokenProvider;
import com.mall.choisinsa.service.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity(debug = true)
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityMemberConfig {
    private final AuthenticationProvider authenticationProvider;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .httpBasic().disable()
                .userDetailsService(securityUserDetailsService)
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .and().ignoring().antMatchers(getAnyMatchersForStaticPathes())
                    .and().ignoring().antMatchers(getAnyMatchersForDynamicPathes());

        };
    }

    private String[] getAnyMatchersForStaticPathes() {
        return new String[]{
                "/img/**",
                "/h2-console/**",
                "favicon.ico",
        };
    }

    private String[] getAnyMatchersForDynamicPathes() {
        return new String[]{
                "/api/members/login"
        };
    }
}
