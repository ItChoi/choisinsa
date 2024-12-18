package com.mall.choisinsa.web.resolver;

import com.mall.choisinsa.annotation.LoginUser;
import core.common.secret.ConstData;
import com.mall.choisinsa.dto.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.Constants;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
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
        boolean hasMemberType = LoginUserDto.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginMemberAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // TODO: JWT 가져와서 체크 @LoginUser 애노테이션을 사용하고 있는 파라미터에 Member 객체 리턴하기.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        request.getHeader("");

        String bearerToken = request.getHeader(Constants.AUTHORIZATION_HEADER_NAME);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(ConstData.AUTHORIZATION_BEARER)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
