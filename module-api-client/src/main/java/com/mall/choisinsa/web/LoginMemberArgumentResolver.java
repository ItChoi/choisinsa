package com.mall.choisinsa.web;

import com.mall.choisinsa.annotation.LoginUser;
import core.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginMemberAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginMemberAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // TODO: JWT 가져와서 체크 @LoginUser 애노테이션을 사용하고 있는 파라미터에 Member 객체 리턴하기.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        request.getHeader("");

        return null;
    }
}
