package com.mall.choisinsa.security.repository;

import com.mall.choisinsa.enumeration.member.MemberType;
import com.mall.choisinsa.security.domain.SecurityMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityMemberRepository extends JpaRepository<SecurityMember, Long> {

    Optional<SecurityMember> findByLoginId(String username);
}
