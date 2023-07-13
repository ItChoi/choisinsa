package com.mall.choisinsa.security.repository;

import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.security.domain.SecurityMemberSnsConnect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityMemberSnsConnectRepository extends JpaRepository<SecurityMemberSnsConnect, Long> {

    Optional<SecurityMemberSnsConnect> findBySnsTypeAndSnsId(SnsType loginType,
                                                             String snsId);
}
