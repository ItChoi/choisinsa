package com.mall.choisinsa.domain;

import com.mall.choisinsa.enumeration.security.AuthorityType;
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

    @Column
    private boolean display;

    @Column
    private boolean directConfig;
}
