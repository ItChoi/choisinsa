package core.repository.category;

import com.mall.choisinsa.enumeration.category.CategoryAreaType;
import com.mall.choisinsa.enumeration.category.CategoryType;
import core.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByAreaTypeAndCodeAndDepth(CategoryAreaType areaType,
                                                     String code,
                                                     int depth);
}