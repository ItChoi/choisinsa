package com.mall.choisinsa.repository;

import com.mall.choisinsa.domain.SecurityMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityMemberRepository extends JpaRepository<SecurityMember, Long> {

    Optional<SecurityMember> findByLoginId(String username);
}
