package core.repository.brand;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static core.domain.brand.QBrand.brand;
import static core.domain.company.QCompany.company;
import static core.domain.member.QAdminMemberCompanyMapping.adminMemberCompanyMapping;
import static core.domain.member.QMember.member;

@RequiredArgsConstructor
public class BrandRepositoryCustomImpl implements BrandRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public boolean isRightBrandMember(Long memberId,
                                      Long companyId,
                                      Long brandId) {

        long count = queryFactory
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
                        member.id.eq(memberId)
                ).fetchCount();


        return count > 0;
    }
}
