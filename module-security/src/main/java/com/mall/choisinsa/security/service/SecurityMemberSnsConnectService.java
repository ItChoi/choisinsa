package com.mall.choisinsa.security.service;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityMemberSnsConnect;
import com.mall.choisinsa.security.repository.SecurityMemberSnsConnectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityMemberSnsConnectService {

    private final SecurityMemberSnsConnectRepository securityMemberSnsConnectRepository;


    @Transactional(readOnly = true)
    public SecurityMemberSnsConnect findBySnsTypeAndSnsIdOrThrow(SnsType snsType,
                                                                 String snsId) {
        return findBySnsTypeAndSnsId(snsType, snsId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.BAD_REQUEST));
    }

    @Transactional(readOnly = true)
    public Optional<SecurityMemberSnsConnect> findBySnsTypeAndSnsId(SnsType snsType,
                                                                    String snsId) {
        return securityMemberSnsConnectRepository.findBySnsTypeAndSnsId(snsType, snsId);
    }
}
