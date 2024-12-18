package core.common.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DomainServiceType {
    ADMIN("관리자 서비스"),
    MEMBER("이용자 서비스");

    private final String text;
}
