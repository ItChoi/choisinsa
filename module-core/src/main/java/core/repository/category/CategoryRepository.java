package core.repository.category;

import core.common.enumeration.category.CategoryAreaType;
import core.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByAreaTypeAndCodeAndDepth(CategoryAreaType areaType,
                                                     String code,
                                                     int depth);
}