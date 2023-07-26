package core.domain.company;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 회사
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Company extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long countryId;

    /**
     * 대표명 (개인 정보 암호화 필요)
     */
    @Column
    private String ceoName;

    /**
     * 회사명
     */
    @Column
    private String name;

    /**
     * 회사 번호 (개인 정보 암호화 필요)
     */
    @Column
    private String phoneNumber;

    /**
     * 사업자 번호
     */
    @Column
    private String bizNumber;

    /**
     * 회사 주소
     */
    @Column
    private String address;

    /**
     * 통신판매업신고
     */
    @Column
    private String eCommerceRegistration;
}
