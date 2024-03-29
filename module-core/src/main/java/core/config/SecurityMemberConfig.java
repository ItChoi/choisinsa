/*
package com.mall.choisinsa.security.config;

import com.mall.choisinsa.security.filter.JwtFilter;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import com.mall.choisinsa.security.service.Oauth2UserService;
import com.mall.choisinsa.security.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@EnableWebSecurity(debug = true)
@Profile("client")
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityMemberConfig {
    private final AuthenticationProvider authenticationProvider;
    private final UserDetailsService securityUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Oauth2UserService oauth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .userDetailsService(securityUserDetailsService)
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .oauth2Login()
                    .userInfoEndpoint()
                    .userService(oauth2UserService);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .and().ignoring().antMatchers(getAnyMatchersForStaticPaths())
                    .and().ignoring().antMatchers(HttpMethod.GET, getMappingAnyMatchersForDynamicPaths())
                    .and().ignoring().antMatchers(HttpMethod.POST, postMappingAnyMatchersForDynamicPaths());

        };
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
                "/login/oauth2/code/**"
        };
    }

    private String[] postMappingAnyMatchersForDynamicPaths() {
        return new String[]{
                "/api/members/login",
                "/api/members",
                "/api/members/{oauthLoginType}",
                "/api/kakao/oauth/token"
        };
    }
}
*/
