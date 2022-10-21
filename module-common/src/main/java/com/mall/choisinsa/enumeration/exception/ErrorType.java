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
    ADMIN_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 관리자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
