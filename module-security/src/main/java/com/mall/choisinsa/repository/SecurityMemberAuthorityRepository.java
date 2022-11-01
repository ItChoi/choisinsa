package com.mall.choisinsa.repository;

import com.mall.choisinsa.domain.SecurityMemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecurityMemberAuthorityRepository extends JpaRepository<SecurityMemberAuthority, Long> {

    List<SecurityMemberAuthority> findAllByMemberId(Long memberId);
}
