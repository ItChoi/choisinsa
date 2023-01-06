package com.mall.choisinsa.security.domain;

import com.mall.choisinsa.enumeration.security.AuthorityType;
import com.mall.choisinsa.domain.BaseDateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "authority")
public class SecurityAuthority extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private AuthorityType authority;

    @Column(name = "is_display")
    private boolean display;

    @Column(name = "is_direct_config")
    private boolean directConfig;
}
