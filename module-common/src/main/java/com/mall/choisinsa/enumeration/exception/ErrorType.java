package com.mall.choisinsa.enumeration.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    SERVER_ERROR_PARAM(HttpStatus.INTERNAL_SERVER_ERROR, "필수 데이터가 존재하지 않습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    BAD_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "요청 정보가 잘못됐습니다."),
    UN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 회원을 찾을 수 없습니다."),
    ADMIN_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 관리자를 찾을 수 없습니다."),
    NOT_AVAILABLE_LOGIN_ID(HttpStatus.BAD_REQUEST, "이용할 수 없는 아이디 형식입니다."),
    NOT_AVAILABLE_PASSWORD(HttpStatus.BAD_REQUEST, "이용할 수 없는 비밀번호 아이디입니다."),
    NOT_SUPPORT_AUTHENTICATION(HttpStatus.INTERNAL_SERVER_ERROR, "지원하지 않는 인증 형식입니다."),
    WRONG_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "지원하지 않는 JWT 토큰입니다."),
    INVALID_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 JWT 토큰입니다."),
    MISMATCH_REQUEST(HttpStatus.BAD_REQUEST, "입력하신 정보가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
