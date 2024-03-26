package com.mall.choisinsa.api.category;

import com.mall.choisinsa.ClientApplicationBaseTest;
import com.mall.choisinsa.enumeration.category.CategoryAreaType;
import com.mall.choisinsa.enumeration.category.CategoryType;
import core.domain.category.Category;
import core.dto.client.response.category.CategoryResponseDto;
import core.dto.client.response.category.CategoryTypeResponseDto;
import core.service.category.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends ClientApplicationBaseTest {

    @MockBean
    private CategoryService categoryService;

    @DisplayName("영역 타입 & 카테고리 타입에 해당하는 카테고리 조회")
    @Test
    void getAllByAreaTypeAndCategoryType() throws Exception {
        //given

        //when
        given(categoryService.findTopCategoryResponseDtoBy(any(), any()))
                .willReturn(generateCategoryResponseDto());

        //then
        this.mockMvc.perform(get("/api/categories/{areaType}/{categoryType}", CategoryAreaType.MAIN, CategoryType.EVENT))
                .andExpect(status().isOk())
                .andDo(document("member_top_category",
                        pathParameters(
                                parameterWithName("areaType").description("area type : " + Arrays.stream(CategoryAreaType.values()).map(CategoryAreaType::name).collect(Collectors.joining(" | "))),
                                parameterWithName("categoryType").description("category type : " + Arrays.stream(CategoryType.values()).map(CategoryType::name).collect(Collectors.joining(" | ")))
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorType").description("예외 타입"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("카테고리 PK"),
                                fieldWithPath("data.areaType").type(JsonFieldType.STRING).description("area type : " + Arrays.stream(CategoryAreaType.values()).map(CategoryAreaType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("data.parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 PK").optional(),
                                fieldWithPath("data.parent").type(JsonFieldType.OBJECT).description("부모").ignored(),
                                fieldWithPath("data.children").type(JsonFieldType.ARRAY).description("자식 카테고리").optional(),
                                fieldWithPath("data.children[].id").type(JsonFieldType.NUMBER).description("카테고리 PK").optional(),
                                fieldWithPath("data.children[].areaType").type(JsonFieldType.STRING).description("area type : " + Arrays.stream(CategoryAreaType.values()).map(CategoryAreaType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("data.children[].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 PK").optional(),
                                fieldWithPath("data.children[].parent").type(JsonFieldType.OBJECT).description("부모").ignored(),
                                fieldWithPath("data.children[].children").type(JsonFieldType.OBJECT).description("부모").ignored(),
                                fieldWithPath("data.children[].categoryType").type(JsonFieldType.STRING).description("category type : " + Arrays.stream(CategoryType.values()).map(CategoryType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("data.children[].code").type(JsonFieldType.STRING).description("카테고리 코드").optional(),
                                fieldWithPath("data.children[].name").type(JsonFieldType.STRING).description("카테고리명").optional(),
                                fieldWithPath("data.children[].depth").type(JsonFieldType.NUMBER).description("카테고리 depth").optional(),
                                fieldWithPath("data.children[].displayOrder").type(JsonFieldType.NUMBER).description("카테고리 표시 순서").optional(),
                                fieldWithPath("data.categoryType").type(JsonFieldType.STRING).description("category type : " + Arrays.stream(CategoryType.values()).map(CategoryType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("data.code").type(JsonFieldType.STRING).description("카테고리 코드"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("카테고리명"),
                                fieldWithPath("data.depth").type(JsonFieldType.NUMBER).description("카테고리 depth"),
                                fieldWithPath("data.displayOrder").type(JsonFieldType.NUMBER).description("카테고리 표시 순서")
                        )
                ));
    }

    @DisplayName("모든 카테고리 타입 조회")
    @Test
    void getCategoryTypeAll() throws Exception {
        //given

        //when
        given(categoryService.findCategoryTypeAll())
                .willReturn(Arrays.stream(CategoryType.values())
                        .map(CategoryTypeResponseDto::new)
                        .collect(Collectors.toList()));

        //then
        this.mockMvc.perform(get("/api/categories/all-type"))
                .andExpect(status().isOk())
                .andDo(document("member_all_category_type",
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorType").description("예외 타입"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("카테고리 타입명"),
                                fieldWithPath("data[].categoryType").type(JsonFieldType.STRING).description("category type : " + Arrays.stream(CategoryType.values()).map(CategoryType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("data[].code").type(JsonFieldType.STRING).description("카테고리 타입 코드")
                        )
                ));
    }

    private CategoryResponseDto generateCategoryResponseDto() {
        return new CategoryResponseDto(initUnsortedTopCategory());
    }

    public static Category initUnsortedTopCategory() {
        return new Category(
                1L,
                CategoryAreaType.MAIN,
                null,
                null,
                Set.of(
                        new Category(
                                2L,
                                CategoryAreaType.MAIN,
                                1L,
                                null,
                                null,
                                "event-c11",
                                "이벤트-c11",
                                "EVENT",
                                2,
                                1
                        ),
                        new Category(
                                3L,
                                CategoryAreaType.MAIN,
                                1L,
                                null,
                                null,
                                "event-c22",
                                "이벤트-c22",
                                "EVENT",
                                2,
                                2
                        )

                ),
                "event",
                "이벤트",
                "EVENT",
                1,
                1
        );
    }

}