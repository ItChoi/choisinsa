package core.repository.brand;

import core.domain.member.Member;

import java.util.Optional;

public interface BrandRepositoryCustom {

    Optional<Member> findBrandAdminBy(Long memberId,
                                      Long companyId,
                                      Long brandId);
}