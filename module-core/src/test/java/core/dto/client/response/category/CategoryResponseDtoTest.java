package core.dto.client.response.category;

import core.domain.category.Category;
import core.domain.category.CategoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryResponseDtoTest {
    Category oneDepthcategory = CategoryTest.initUnsortedTopCategory();

    @BeforeEach
    void beforeEach() {
        assertThat(oneDepthcategory).isNotNull();
        assertThat(oneDepthcategory.getChildren()).isNotNull();
    }

    @DisplayName("카테고리 계층 구조 DTO 변환, 정렬 테스트")
    @Test
    void 카테고리_계층_구조_DTO_변환_정렬_테스트() {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(oneDepthcategory);
        assertThat(categoryResponseDto).isNotNull();

        int originalTwoDepthSize = oneDepthcategory.getChildren().size();
        int convertedTwoDepthSize = categoryResponseDto.getChildren().size();
        assertThat(originalTwoDepthSize).isEqualTo(convertedTwoDepthSize);
    }

}