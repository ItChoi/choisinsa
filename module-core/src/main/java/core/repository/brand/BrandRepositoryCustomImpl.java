package core.repository.brand;

import core.common.enumeration.member.MemberType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.domain.member.Member;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static core.domain.brand.QBrand.brand;
import static core.domain.company.QCompany.company;
import static core.domain.member.QAdminMemberCompanyMapping.adminMemberCompanyMapping;
import static core.domain.member.QMember.member;

@RequiredArgsConstructor
public class BrandRepositoryCustomImpl implements BrandRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<Member> findBrandAdminBy(Long memberId,
                                             Long companyId,
                                             Long brandId) {

        return Optional.ofNullable(queryFactory
                .select(
                        member
                )
                .from(member)
                .innerJoin(adminMemberCompanyMapping)
                .on(adminMemberCompanyMapping.memberId.eq(member.id)
                        .and(adminMemberCompanyMapping.companyId.eq(companyId)))
                .innerJoin(company)
                .on(company.id.eq(adminMemberCompanyMapping.companyId))
                .innerJoin(brand)
                .on(brand.companyId.eq(company.id)
                        .and(brand.id.eq(brandId)))
                .where(
                        member.id.eq(memberId),
                        member.memberType.eq(MemberType.BRAND_ADMIN)
                ).fetchOne());
    }
}
