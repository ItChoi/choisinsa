package com.mall.choisinsa.web.config;

import com.mall.choisinsa.web.filter.JwtFilter;
import com.mall.choisinsa.web.provider.JwtTokenProvider;
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


@Profile("client")
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityMemberConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService userDetailsService;
    //private final Oauth2UserService oauth2UserService;

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

        http.httpBasic().disable();
        http.addFilterBefore(new JwtFilter(getClientJwtTokenProvider()), UsernamePasswordAuthenticationFilter.class);
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
                "/docs/**",
                "/members/iamport-verification**",
                "/api/kakao/oauth/authorization-code",
                "/api/kakao/oauth/authorize",
                "/login/oauth2/code/**",
                "/api/members/{loginId}/recommender",
                "/api/categories/types",
                "/api/categories/{areaType}/{categoryType}",
                "/api/items/item-count-per-category",
                "/api/items/priority-item-sales-per-category",
        };
    }

    private String[] postMappingAnyMatchersForDynamicPaths() {
        return new String[]{
                "/api/members/login",
                "/api/members",
                "/api/members/{oauthLoginType}",
                "/api/kakao/oauth/token",
        };
    }

    @Bean
    public JwtTokenProvider getClientJwtTokenProvider() {
        return new JwtTokenProvider();
    }
}
