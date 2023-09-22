package com.mall.choisinsa.security.config;

import com.mall.choisinsa.security.filter.JwtFilter;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("admin")
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class TestSecurityAdminConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().antMatchers(getAnyMatchersForStaticPaths());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationProvider)
                .userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(getMappingAnyMatchersForDynamicPaths()).permitAll()
                .antMatchers(postMappingAnyMatchersForDynamicPaths()).permitAll()
                .anyRequest()
                .authenticated();

        http.headers()
                .frameOptions()
                .sameOrigin();

        http.httpBasic();
        http.addFilterBefore(new JwtFilter(getAdminJwtTokenProvider()), UsernamePasswordAuthenticationFilter.class);
    }

    private String[] getAnyMatchersForStaticPaths() {
        return new String[]{
                "/img/**",
                "/h2-console/**",
                "favicon.ico",
        };
    }

    private String[] getMappingAnyMatchersForDynamicPaths() {
        return new String[]{
                "/docs/**"
        };
    }

    private String[] postMappingAnyMatchersForDynamicPaths() {
        return new String[]{
                "/api/admin/members/login"
        };
    }

    @Bean
    public JwtTokenProvider getAdminJwtTokenProvider() {
        return new JwtTokenProvider();
    }
}
