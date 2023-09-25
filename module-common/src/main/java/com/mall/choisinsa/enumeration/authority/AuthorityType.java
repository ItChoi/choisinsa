package com.mall.choisinsa.enumeration.authority;

import com.mall.choisinsa.enumeration.DomainServiceType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum AuthorityType {
    SUPER("슈퍼 관리자", DomainServiceType.ADMIN),
    ADMIN("관리자", DomainServiceType.ADMIN),
    MEMBER("이용자", DomainServiceType.MEMBER);

    private final String text;
    private final DomainServiceType serviceType;

    public static boolean isRightAuthority(AuthorityType type,
                                           boolean isAdmin) {

        DomainServiceType domainServiceType = isAdmin ? DomainServiceType.ADMIN : DomainServiceType.MEMBER;
        return getAuthoritiesTypeBy(domainServiceType).contains(type);
    }

    public static List<AuthorityType> getAuthoritiesTypeBy(DomainServiceType domainServiceType) {
        return Arrays.stream(AuthorityType.values())
                .filter(authorityType -> authorityType.serviceType == domainServiceType)
                .collect(Collectors.toList());
    }

    public static List<AuthorityType> getAuthoritiesTypeBy(boolean isAdmin) {
        DomainServiceType serviceType = isAdmin ? DomainServiceType.ADMIN : DomainServiceType.MEMBER;
        return getAuthoritiesTypeBy(serviceType);
    }
}
