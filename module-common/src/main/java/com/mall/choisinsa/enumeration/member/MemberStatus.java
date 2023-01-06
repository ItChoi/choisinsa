package com.mall.choisinsa.enumeration.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {
    NORMAL("정상"),
    LEAVE("회원 탈퇴"),
    BAN("회원 영구 정지"),
    STOP("일시 정지");

    private final String text;
}
