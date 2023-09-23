package com.mall.choisinsa.security.service;

import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.security.domain.SecurityAuthority;
import com.mall.choisinsa.security.domain.SecurityAuthorityMenu;
import com.mall.choisinsa.security.dto.menu.SecurityAuthorityApplicationReadyDto;
import com.mall.choisinsa.security.repository.SecurityAuthorityMenuRepository;
import com.mall.choisinsa.security.repository.SecurityAuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SecurityAuthorityService {
    private final SecurityAuthorityRepository authorityRepository;
    private final SecurityAuthorityMenuRepository authorityMenuRepository;

}
