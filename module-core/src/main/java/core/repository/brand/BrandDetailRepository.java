package core.repository.brand;

import core.domain.brand.BrandDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrandDetailRepository extends JpaRepository<BrandDetail, Long> {

}