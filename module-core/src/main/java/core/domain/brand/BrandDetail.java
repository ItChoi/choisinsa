package core.domain.brand;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 브랜드 상세
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BrandDetail extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long brandId;

    /**
     * 브랜드 로고 파일 경로
     */
    @Column
    private String logoFileUrl;

    /**
     * 브랜드 로고 파일명
     */
    @Column
    private String logoFilename;

    /**
     * 브랜드 설명
     */
    @Column
    private String description;

    /**
     * 브랜드 웹 사이트
     */
    @Column
    private String webSite;

    /**
     * 브랜드 이메일 (개인 정보 암호화 필요)
     */
    @Column
    private String email;

    /**
     * 영업 소재지 주소
     */
    @Column
    private String address;

    /**
     * 담당자 휴대폰 번호 (개인 정보 암호화 필요)
     */
    @Column
    private String managerPhoneNumber;
}
