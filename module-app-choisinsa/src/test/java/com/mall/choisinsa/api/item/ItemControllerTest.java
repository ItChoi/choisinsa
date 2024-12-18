package com.mall.choisinsa.api.item;

import com.mall.choisinsa.ClientApplicationBaseTest;
import core.common.enumeration.item.ItemEditorContentType;
import core.common.enumeration.item.ItemOptionType;
import core.common.enumeration.item.ItemStatus;
import core.common.enumeration.item.TargetType;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.response.item.*;
import core.dto.client.response.stats.ItemSalesStatsWrapperResponseDto;
import core.dto.client.response.stats.PriorityItemSalesStatsPerItemCategoryResponseDto;
import core.service.item.ItemService;
import core.service.stats.ItemStatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest extends ClientApplicationBaseTest {

    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemStatsService itemStatsService;

    @DisplayName("아이템 상세 조회")
    @Test
    void getItemDetail() throws Exception {
        //given
        ItemDetailRequestDto itemDetail = new ItemDetailRequestDto();
        itemDetail.setBrandId(1L);

        //when
        given(itemService.findItemDetailInfoResponseDtoBy(any(), any()))
                .willReturn(generateItemDetailInfoResponseDto());

        //then
        this.mockMvc.perform(get("/api/items/{itemId}", 1L)
//                        .with(user(generateMemberSecurityMemberDto()))
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andDo(document("member_get_item_detail",
                        pathParameters(
                                parameterWithName("itemId").description("상품 PK")
                        ),
                        requestParameters(
                                parameterWithName("brandId").description("브랜드 PK")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorType").description("예외 타입"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data.item.id").type(JsonFieldType.NUMBER).description("상품 PK"),
                                fieldWithPath("data.item.itemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("data.item.brandId").type(JsonFieldType.NUMBER).description("브랜드 PK"),
                                fieldWithPath("data.item.status").type(JsonFieldType.STRING).description("상품 상태: " + Arrays.stream(ItemStatus.values()).map(ItemStatus::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("data.item.targetType").type(JsonFieldType.STRING).description("타겟 타입: " + Arrays.stream(TargetType.values()).map(TargetType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("data.item.nameEn").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("data.item.nameKo").type(JsonFieldType.STRING).description("상품명 영문"),
                                fieldWithPath("data.item.price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("data.item.fileUrl").type(JsonFieldType.STRING).description("상품 메인 이미지").optional(),
                                fieldWithPath("data.item.filename").type(JsonFieldType.STRING).description("상품 메인 이미지 파일명").optional(),
                                fieldWithPath("data.item.totalStockQuantity").type(JsonFieldType.NUMBER).description("총 재고 수량"),
                                fieldWithPath("data.item.canPurchaseItemStatus").type(JsonFieldType.BOOLEAN).description("구매 가능한 상품 상태"),
                                fieldWithPath("data.item.isDisplayItemStatus").type(JsonFieldType.BOOLEAN).description("상품 노출 가능 상태"),
                                fieldWithPath("data.item.itemDetail").type(JsonFieldType.OBJECT).description("아이템 상세"),
                                fieldWithPath("data.item.itemDetail.id").type(JsonFieldType.NUMBER).description("아이템 상세 PK"),
                                fieldWithPath("data.item.itemDetail.itemNumber").type(JsonFieldType.STRING).description("품목 번호").optional(),
                                fieldWithPath("data.item.itemDetail.materialName").type(JsonFieldType.STRING).description("제품 소재 (면 100% 등)"),
                                fieldWithPath("data.item.itemDetail.manufacturer").type(JsonFieldType.STRING).description("제조사"),
                                fieldWithPath("data.item.itemDetail.manufacturerCountryName").type(JsonFieldType.STRING).description("제조국"),
                                fieldWithPath("data.item.itemDetail.manufacturingDate").type(JsonFieldType.STRING).description("제조연월"),
                                fieldWithPath("data.item.itemDetail.warrantyPeriod").type(JsonFieldType.STRING).description("품질보증기간"),
                                fieldWithPath("data.item.itemThumbnails").type(JsonFieldType.ARRAY).description("상품 썸네일"),
                                fieldWithPath("data.item.itemThumbnails[].id").type(JsonFieldType.NUMBER).description("상품 썸네일 PK"),
                                fieldWithPath("data.item.itemThumbnails[].displayOrder").type(JsonFieldType.NUMBER).description("상품 썸네일 노출 순서"),
                                fieldWithPath("data.item.itemThumbnails[].fileUrl").type(JsonFieldType.STRING).description("상품 썸네일 이미지"),
                                fieldWithPath("data.item.itemThumbnails[].filename").type(JsonFieldType.STRING).description("상품 썸네일 이미지 파일명"),
                                fieldWithPath("data.itemOptions").type(JsonFieldType.ARRAY).description("상품 옵션").optional(),
                                fieldWithPath("data.itemOptions[].id").type(JsonFieldType.NUMBER).description("상품 옵션 PK").optional(),
                                fieldWithPath("data.itemOptions[].itemId").type(JsonFieldType.NUMBER).description("상품 PK").optional(),
                                fieldWithPath("data.itemOptions[].itemOptionType").type(JsonFieldType.STRING).description("상품 옵션 타입: " + Arrays.stream(ItemOptionType.values()).map(ItemOptionType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("data.itemOptions[].displayOrder").type(JsonFieldType.NUMBER).description("상품 옵션 표시 순서").optional(),
                                fieldWithPath("data.itemOptions[].itemOptionDetails[].id").type(JsonFieldType.NUMBER).description("상품 옵션 상세 PK").optional(),
                                fieldWithPath("data.itemOptions[].itemOptionDetails[].itemOptionId").type(JsonFieldType.NUMBER).description("상품 옵션 PK").optional(),
                                fieldWithPath("data.itemOptions[].itemOptionDetails[].name").type(JsonFieldType.STRING).description("상품 옵션명").optional(),
                                fieldWithPath("data.itemOptions[].itemOptionDetails[].addPrice").type(JsonFieldType.NUMBER).description("상품 옵션 추가 금액").optional(),
                                fieldWithPath("data.itemOptions[].itemOptionDetails[].stockQuantity").type(JsonFieldType.NUMBER).description("상품 옵션 재고 수량").optional(),
                                fieldWithPath("data.itemOptions[].itemOptionDetails[].displayOrder").type(JsonFieldType.NUMBER).description("상품 옵션 상세 표시 순서").optional(),
                                fieldWithPath("data.itemEditorInfo").type(JsonFieldType.OBJECT).description("상품 에디터 정보").optional(),
                                fieldWithPath("data.itemEditorInfo.id").type(JsonFieldType.NUMBER).description("상품 에디터 정보 PK").optional(),
                                fieldWithPath("data.itemEditorInfo.title").type(JsonFieldType.STRING).description("상품 에디터 정보 제목").optional(),
                                fieldWithPath("data.itemEditorInfo.isMain").type(JsonFieldType.BOOLEAN).description("상품 에디터 정보 메인 여부").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents").type(JsonFieldType.ARRAY).description("상품 에디터 내용").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].id").type(JsonFieldType.NUMBER).description("상품 에디터 내용 PK").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorInfoId").type(JsonFieldType.NUMBER).description("상품 에디터 정보 PK").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].type").type(JsonFieldType.STRING).description("상품 에디터 내용 타입: " + Arrays.stream(ItemEditorContentType.values()).map(ItemEditorContentType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].displayOrder").type(JsonFieldType.NUMBER).description("상품 에디터 내용 표시 순서").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorImage").type(JsonFieldType.OBJECT).description("상품 에디터 내용 - 이미지").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorImage.id").type(JsonFieldType.NUMBER).description("상품 에디터 내용 - 이미지 PK").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorImage.fileUrl").type(JsonFieldType.STRING).description("상품 에디터 내용 - 이미지 리모트 이미지 경로").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorImage.filename").type(JsonFieldType.STRING).description("상품 에디터 내용 - 이미지 리모트 이미지명").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorMarkupText").type(JsonFieldType.OBJECT).description("상품 에디터 내용 - 마크업 텍스트").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorMarkupText.id").type(JsonFieldType.NUMBER).description("상품 에디터 내용 - 마크업 텍스트 PK").optional(),
                                fieldWithPath("data.itemEditorInfo.itemEditorContents[].itemEditorMarkupText.text").type(JsonFieldType.STRING).description("상품 에디터 내용 - 마크업 텍스트").optional(),
                                fieldWithPath("data.itemHashTags").type(JsonFieldType.ARRAY).description("상품 해시태그").optional(),
                                fieldWithPath("data.itemHashTags[].id").type(JsonFieldType.NUMBER).description("상품 해시태그 PK").optional(),
                                fieldWithPath("data.itemHashTags[].content").type(JsonFieldType.STRING).description("상품 해시태그 내용").optional()
                        )
                ));
    }

    @DisplayName("모든 카테고리별(계층 구조) 상품 개수 조회")
    @Test
    void getItemCountByCategory() throws Exception {
        //given

        //when
        given(itemService.findItemCountAllPerCategoryApplicationReadyDtoAll())
                .willReturn(generateItemCountAllPerCategoryApplicationReadyDto());

        //then
        this.mockMvc.perform(get("/api/items/item-count-per-category"))
                .andExpect(status().isOk())
                .andDo(document("member_get_item_count_per_category",
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorType").description("예외 타입"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data[].itemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("data[].code").type(JsonFieldType.STRING).description("상품 카테고리 코드"),
                                fieldWithPath("data[].parentCode").type(JsonFieldType.STRING).description("상품 카테고리 부모 코드").optional(),
                                fieldWithPath("data[].depth").type(JsonFieldType.NUMBER).description("상품 계층"),
                                fieldWithPath("data[].displayOrder").type(JsonFieldType.NUMBER).description("상품 노출 순서"),
                                fieldWithPath("data[].categoryName").type(JsonFieldType.STRING).description("상품 카테고리명"),
                                fieldWithPath("data[].itemCount").type(JsonFieldType.NUMBER).description("상품 상품 개수"),
                                fieldWithPath("data[].includedLowerCategoryTotalItemCount").type(JsonFieldType.NUMBER).description("하위 상품 포함 개수"),
                                fieldWithPath("data[].children[]").type(JsonFieldType.ARRAY).description("자식 객체 리스트").optional(),
                                fieldWithPath("data[].children[].itemCategoryId").type(JsonFieldType.NUMBER).description("자식 상품 카테고리 코드").optional(),
                                fieldWithPath("data[].children[].code").type(JsonFieldType.STRING).description("자식 상품 카테고리 부모 코드").optional(),
                                fieldWithPath("data[].children[].parentCode").type(JsonFieldType.STRING).description("자식 상품 계층").optional(),
                                fieldWithPath("data[].children[].depth").type(JsonFieldType.NUMBER).description("자식 상품 노출 순서").optional(),
                                fieldWithPath("data[].children[].displayOrder").type(JsonFieldType.NUMBER).description("자식 상품 카테고리명").optional(),
                                fieldWithPath("data[].children[].categoryName").type(JsonFieldType.STRING).description("자식 상품 상품 개수").optional(),
                                fieldWithPath("data[].children[].itemCount").type(JsonFieldType.NUMBER).description("자식 하위 상품 포함 개수").optional(),
                                fieldWithPath("data[].children[].includedLowerCategoryTotalItemCount").type(JsonFieldType.NUMBER).description("자식 하위 상품 포함 개수").optional(),
                                fieldWithPath("data[].children[].children[]").type(JsonFieldType.ARRAY).description("자식 하위 상품 포함 개수").ignored()
                        )
                ));
    }

    @DisplayName("모든 카테고리별 상품 판매 통계 정보 조회")
    @Test
    void getPriorityItemSalesPerCategory() throws Exception {
        //given
        Integer yera = 2023;

        //when
        given(itemStatsService.findItemSalesStatsWrapperResponseDtoBy(any()))
                .willReturn(generateItemSalesStatsWrapperResponseDto());

        //then
        this.mockMvc.perform(get("/api/items/priority-item-sales-per-category")
                        .param("year", "2003"))
                .andExpect(status().isOk())
                .andDo(document("member_get_priority_item_sales_per_category",
                        requestParameters(
                                parameterWithName("year").description("파티션 연도")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorType").description("예외 타입"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data.priorityItemSalesStatistics[]").type(JsonFieldType.ARRAY).description("상품 카테고리 PK"),
                                fieldWithPath("data.priorityItemSalesStatistics[].rootItemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK").optional(),
                                fieldWithPath("data.priorityItemSalesStatistics[].parentItemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK").optional(),
                                fieldWithPath("data.priorityItemSalesStatistics[].itemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("data.priorityItemSalesStatistics[].itemId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("data.priorityItemSalesStatistics[].totalQuantity").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),

                                fieldWithPath("data.priorityItemSalesStatisticsWithRootParentId").type(JsonFieldType.OBJECT).description("상품 카테고리 PK"),
                                fieldWithPath("data.priorityItemSalesStatisticsWithRootParentId.*[].rootItemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK").optional(),
                                fieldWithPath("data.priorityItemSalesStatisticsWithRootParentId.*[].parentItemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK").optional(),
                                fieldWithPath("data.priorityItemSalesStatisticsWithRootParentId.*[].itemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("data.priorityItemSalesStatisticsWithRootParentId.*[].itemId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("data.priorityItemSalesStatisticsWithRootParentId.*[].totalQuantity").type(JsonFieldType.NUMBER).description("상품 카테고리 PK")
                        )
                ));
    }

    private ItemSalesStatsWrapperResponseDto generateItemSalesStatsWrapperResponseDto() {
        List<PriorityItemSalesStatsPerItemCategoryResponseDto> priorityItemSalesStatistics = List.of(
                new PriorityItemSalesStatsPerItemCategoryResponseDto(
                        null,
                        null,
                        1L,
                        111L,
                        15
                ),
                new PriorityItemSalesStatsPerItemCategoryResponseDto(
                        1L,
                        1L,
                        2L,
                        111L,
                        15
                )
        );

        Map<Long, List<PriorityItemSalesStatsPerItemCategoryResponseDto>> priorityItemSalesStatisticsWithItemCategoryId = new LinkedHashMap<>();
        for (PriorityItemSalesStatsPerItemCategoryResponseDto priorityItemSalesStats : priorityItemSalesStatistics) {
            Long key = priorityItemSalesStats.getRootItemCategoryId() == null ? priorityItemSalesStats.getItemCategoryId() : priorityItemSalesStats.getRootItemCategoryId();

            List<PriorityItemSalesStatsPerItemCategoryResponseDto> values = priorityItemSalesStatisticsWithItemCategoryId.get(key);
            if (CollectionUtils.isEmpty(values)) {
                values = new ArrayList<>();
                priorityItemSalesStatisticsWithItemCategoryId.put(key, values);
            }

            values.add(priorityItemSalesStats);
        }

        return new ItemSalesStatsWrapperResponseDto(priorityItemSalesStatistics, priorityItemSalesStatisticsWithItemCategoryId);
    }

    private List<ItemCountAllPerCategoryApplicationReadyDto> generateItemCountAllPerCategoryApplicationReadyDto() {
        return List.of(
                new ItemCountAllPerCategoryApplicationReadyDto(
                        1L,
                        "TEST_ITEM_CATEGORY_CODE",
                        null,
                        1,
                        1,
                        "테스트 상품 카테고리",
                        3L,
                        5L,
                        Set.of(
                                new ItemCountAllPerCategoryApplicationReadyDto(
                                        2L,
                                        "TEST_ITEM_CATEGORY_CODE_CHILD",
                                        "TEST_ITEM_CATEGORY_CODE",
                                        2,
                                        1,
                                        "테스트 상품 카테고리",
                                        2L,
                                        2L,
                                        Set.of()
                                )
                        )
                )
        );
    }

    private ItemDetailInfoResponseDto generateItemDetailInfoResponseDto() {
        ItemResponseDto item = generateItemResponseDto();
        List<ItemOptionResponseDto> itemOptions = generateItemOptionResponseDtos();
        ItemEditorInfoResponseDto itemEditorInfo = generateItemEditorInfoResponseDto();
        List<ItemHashTagResponseDto> itemHashTags = generateItemHashTagResponseDtos();

        return new ItemDetailInfoResponseDto(item, itemOptions, itemEditorInfo, itemHashTags);
    }

    private static List<ItemHashTagResponseDto> generateItemHashTagResponseDtos() {
        return List.of(
                new ItemHashTagResponseDto(1L, "짱이뿜"),
                new ItemHashTagResponseDto(2L, "폴햄"),
                new ItemHashTagResponseDto(3L, "굉장해")
        );
    }

    private static ItemEditorInfoResponseDto generateItemEditorInfoResponseDto() {
        ItemEditorImageResponseDto image = new ItemEditorImageResponseDto();
        image.setId(1L);
        image.setFileUrl("remote image url");
        image.setFilename("remote filename");

        ItemEditorMarkupTextResponseDto markupText = new ItemEditorMarkupTextResponseDto();
        markupText.setId(1L);
        markupText.setText("이거 멋진 반팔티예요.");

        ItemEditorContentResponseDto content1 = new ItemEditorContentResponseDto();
        content1.setId(1L);
        content1.setItemEditorInfoId(1L);
        content1.setType(ItemEditorContentType.IMAGE);
        content1.setDisplayOrder(1);
        content1.setItemEditorImage(image);
        ItemEditorContentResponseDto content2 = new ItemEditorContentResponseDto();
        content2.setId(2L);
        content2.setItemEditorInfoId(1L);
        content2.setType(ItemEditorContentType.MARKUP_TEXT);
        content2.setDisplayOrder(2);
        content2.setItemEditorMarkupText(markupText);

        ItemEditorInfoResponseDto itemEditorInfoResponseDto = new ItemEditorInfoResponseDto();
        itemEditorInfoResponseDto.setId(1L);
        itemEditorInfoResponseDto.setTitle("폴햄 반팔 에디터 정보");
        itemEditorInfoResponseDto.setIsMain(true);
        itemEditorInfoResponseDto.setItemEditorContents(
                List.of(
                        content1,
                        content2
                )
        );

        return itemEditorInfoResponseDto;
    }

    private static List<ItemOptionResponseDto> generateItemOptionResponseDtos() {
        ItemOptionDetailResponseDto itemOptionDetailResponseDto1 = new ItemOptionDetailResponseDto();
        itemOptionDetailResponseDto1.setId(1L);
        itemOptionDetailResponseDto1.setItemOptionId(1L);
        itemOptionDetailResponseDto1.setName("S");
        itemOptionDetailResponseDto1.setAddPrice(0L);
        itemOptionDetailResponseDto1.setStockQuantity(5);
        itemOptionDetailResponseDto1.setDisplayOrder(1);

        ItemOptionDetailResponseDto itemOptionDetailResponseDto2 = new ItemOptionDetailResponseDto();
        itemOptionDetailResponseDto2.setId(1L);
        itemOptionDetailResponseDto2.setItemOptionId(2L);
        itemOptionDetailResponseDto2.setName("M");
        itemOptionDetailResponseDto2.setAddPrice(0L);
        itemOptionDetailResponseDto2.setStockQuantity(4);
        itemOptionDetailResponseDto2.setDisplayOrder(2);

        ItemOptionResponseDto itemOptionResponseDto = new ItemOptionResponseDto();
        itemOptionResponseDto.setId(1L);
        itemOptionResponseDto.setItemId(1L);
        itemOptionResponseDto.setItemOptionType(ItemOptionType.SIZE);
        itemOptionResponseDto.setDisplayOrder(1);
        itemOptionResponseDto.setItemOptionDetails(
                List.of(
                        itemOptionDetailResponseDto1,
                        itemOptionDetailResponseDto2
                )
        );

        return List.of(itemOptionResponseDto);
    }

    private ItemResponseDto generateItemResponseDto() {
        ItemResponseDto itemResponseDto = new ItemResponseDto(
                1L,
                1L,
                1L,
                ItemStatus.ON_SALE,
                TargetType.MALE,
                "폴햄 반팔 티셔츠",
                "폴햄 반팔 티셔츠",
                39000L,
                "remote image url",
                "image test.jpg",
                15,
                new ItemDetailResponseDto(
                        1L,
                        "abc-1231233",
                        "면 100%",
                        "제조사임니다",
                        "중국",
                        LocalDateTime.now(),
                        "1년"
                )
        );
        itemResponseDto.setCanPurchaseItemStatus(true);
        itemResponseDto.setIsDisplayItemStatus(true);

        itemResponseDto.setItemThumbnails(
                List.of(
                        new ItemThumbnailResponseDto(
                                1L,
                                1,
                                "remote image1 url",
                                "thumbnail test1.jpg"
                        ),
                        new ItemThumbnailResponseDto(
                                2L,
                                2,
                                "remote image2 url",
                                "thumbnail test2.jpg"
                        )
                ));

        return itemResponseDto;
    }
}