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
    NOT_AVAILABLE_DATA(HttpStatus.BAD_REQUEST, "이용할 수 없는 %s입니다."),
    NOT_SUPPORT_AUTHENTICATION(HttpStatus.INTERNAL_SERVER_ERROR, "지원하지 않는 인증 형식입니다."),
    WRONG_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "지원하지 않는 JWT 토큰입니다."),
    INVALID_JWT_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 JWT 토큰입니다."),
    MISMATCH_REQUEST(HttpStatus.BAD_REQUEST, "입력하신 정보가 일치하지 않습니다."),
    NOT_EXISTS_REQUIRED_DATA(HttpStatus.BAD_REQUEST, "필수 데이터가 존재하지 않습니다."),
    MISMATCH_AUTHORITY(HttpStatus.BAD_REQUEST, "권한이 일치하지 않습니다."),
    ALREADY_EXISTS_DATA(HttpStatus.BAD_REQUEST, "%s이(가) 이미 존재합니다."),
    ONLY_AVAILABLE_SERVICE_FOR_SITE(HttpStatus.BAD_REQUEST, "사이트 회원만 가능한 서비스입니다."),
    CAN_NOT_JSON_CONVERT(HttpStatus.BAD_REQUEST, "json convert가 되지 않았습니다. 데이터를 확인해주세요."),
    NOT_EXISTS_ITEM_CATEGORY(HttpStatus.BAD_REQUEST, "아이템 카테고리가 존재하지 않습니다."),
    CAN_NOT_EXCEED_ITEM_TOTAL_QUANTITY_WITH_OPTION_ITEM_TOTAL_QUANTITY(HttpStatus.BAD_REQUEST, "옵션 아이템 총 수량으로 아이템의 총 수량을 초과할 수 없습니다."),
    NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST, "상품을 찾을 수 없습니다."),
    NOT_FOUND_ITEM_OPTION(HttpStatus.BAD_REQUEST, "상품 옵션을 찾을 수 없습니다."),
    NOT_FOUND_ITEM_DETAIL(HttpStatus.BAD_REQUEST, "상품 상세를 찾을 수 없습니다."),
    NOT_FOUND_ITEM_EDITOR_INFO(HttpStatus.BAD_REQUEST, "상품 에디터 정보를 찾을 수 없습니다."),
    NOT_FOUND_ITEM_EDITOR_CONTENT(HttpStatus.BAD_REQUEST, "상품 에디터 내용을 찾을 수 없습니다."),
    CAN_NOT_CHANGE_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "상품 에디터 내용의 타입은 변경할 수 없습니다. 삭제 후 등록해주세요."),
    NOT_FOUND_ITEM_EDITOR_MARKUP_TEXT(HttpStatus.BAD_REQUEST, "상품 에디터 마크업 텍스트를 찾을 수 없습니다."),
    NOT_FOUND_ITEM_OPTION_DETAIL(HttpStatus.BAD_REQUEST, "상품 옵션 상세를 찾을 수 없습니다."),
    NOT_FOUND_ITEM_THUMBNAIL(HttpStatus.BAD_REQUEST, "상품 썸네일 이미지를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    public static String formatErrorMsg(ErrorType errorType,
                                        String... messages) {
        return String.format(errorType.message, messages);
    }
}
