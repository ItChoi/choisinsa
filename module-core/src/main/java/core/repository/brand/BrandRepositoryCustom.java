package core.repository.brand;

public interface BrandRepositoryCustom {

    boolean isRightBrandMember(Long memberId,
                               Long companyId,
                               Long brandId);
}