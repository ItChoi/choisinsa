package core.service.category;

import core.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.category.CategoryAreaType;
import com.mall.choisinsa.enumeration.category.CategoryType;
import core.common.exception.ErrorType;
import core.domain.category.Category;
import core.dto.client.response.category.CategoryResponseDto;
import core.dto.client.response.category.CategoryTypeResponseDto;
import core.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryTypeResponseDto> findCategoryTypeAll() {
        return Arrays.stream(CategoryType.values())
                .map(CategoryTypeResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponseDto findTopCategoryResponseDtoBy(CategoryAreaType areaType,
                                                            CategoryType categoryType) {
        return findCategoryResponseDtoBy(areaType, categoryType, 1);
    }


    private CategoryResponseDto findCategoryResponseDtoBy(CategoryAreaType areaType,
                                                          CategoryType categoryType,
                                                          int depth) {
        return toCategoryResponseDtoAndSortBy(areaType, categoryType, depth);
    }

    private CategoryResponseDto toCategoryResponseDtoAndSortBy(CategoryAreaType areaType,
                                                               CategoryType categoryType,
                                                               int depth) {
        if (areaType == null || categoryType == null || depth <= 0) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        Category category = categoryRepository.findByAreaTypeAndCodeAndDepth(areaType, categoryType.getCode(), depth)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.BAD_REQUEST));

        return convertToCategoryResponseDtoAndSort(category);
    }

    private CategoryResponseDto convertToCategoryResponseDtoAndSort(Category category) {
        return new CategoryResponseDto(category);
    }
}
