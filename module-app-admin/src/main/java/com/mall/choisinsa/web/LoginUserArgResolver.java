package com.mall.choisinsa.web;

import com.mall.choisinsa.web.annotation.LoginUser;
import core.service.member.CoreMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

//@RequiredArgsConstructor
//@Component
//public class LoginUserArgResolver implements HandlerMethodArgumentResolver {
//
//    private final CoreMemberService coreMemberService;
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(LoginUser.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter,
//                                  ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest,
//                                  WebDataBinderFactory binderFactory) throws Exception {
//        return coreMemberService.getLoginUser();
//    }
//}
