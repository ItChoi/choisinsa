package com.mall.choisinsa.api.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.AdminApplicationBaseTest;
import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
import com.mall.choisinsa.enumeration.item.ItemOptionType;
import com.mall.choisinsa.enumeration.item.TargetType;
import core.dto.admin.request.item.*;
import core.service.item.AdminItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminItemController.class)
class AdminItemControllerTest extends AdminApplicationBaseTest {
    @MockBean
    private AdminItemService adminItemService;

    @DisplayName("아이템 등록 (파일)")
    @Test
    void insertItemWithFile() throws Exception {
        //given
        MockMultipartFile itemMainImageFile = new MockMultipartFile(
                "file",         // 파일 파라미터 이름
                "example.txt",               // 원본 파일 이름
                "text/plain",                // 파일 타입 (MIME 타입)
                "This is the file content".getBytes()  // 파일 내용을 바이트 배열로 지정
        );

        MockMultipartFile itemThumbnailImageFile = new MockMultipartFile(
                "itemThumbnails[0].file",         // 파일 파라미터 이름
                "example111.txt",               // 원본 파일 이름
                "text/plain",                // 파일 타입 (MIME 타입)
                "This is the file content".getBytes()  // 파일 내용을 바이트 배열로 지정
        );

        AdminItemRequestDto requestDto = generateAdminItemRequestDto();

        //when

        //then
        this.mockMvc.perform(multipart("/api/admin/items")
                        .file(itemMainImageFile)
                        .file(itemThumbnailImageFile)
                        .with(user(generateAdminSecurityMemberDto()))
                        .contentType("multipart/form-data")
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andDo(document("admin_insert_item",
                        requestParts(
                                partWithName("file").description("상품 메인 이미지"),
                                partWithName("itemThumbnails[0].file").description("상품 썸네일 이미지")
                        ),
                        requestFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 PK"),
                                fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("브랜드 PK"),
                                fieldWithPath("itemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("itemNameEn").type(JsonFieldType.STRING).description("상품명 영문"),
                                fieldWithPath("itemNameKo").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("targetType").type(JsonFieldType.STRING).description("타겟 타입: " + Arrays.stream(TargetType.values()).map(TargetType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("totalStockQuantity").type(JsonFieldType.NUMBER).description("상품 총 재고 수량"),
                                fieldWithPath("file").type(JsonFieldType.STRING).description("상품 메인 이미지").optional(),
                                fieldWithPath("availableData").type(JsonFieldType.STRING).description("").ignored(),
                                fieldWithPath("itemOptions").type(JsonFieldType.ARRAY).description("상품 옵션"),
                                fieldWithPath("itemOptions[].itemOptionId").type(JsonFieldType.NUMBER).description("상품 옵션"),
                                fieldWithPath("itemOptions[].itemOptionType").type(JsonFieldType.STRING).description("상품 옵션 타입: " + Arrays.stream(ItemOptionType.values()).map(ItemOptionType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("itemOptions[].displayOrder").type(JsonFieldType.NUMBER).description("상품 옵션 표시 순서"),
                                fieldWithPath("itemOptions[].registrableData").type(JsonFieldType.NUMBER).description("").ignored(),
                                fieldWithPath("itemOptions[].itemOptionDetails").type(JsonFieldType.ARRAY).description("상품 옵션"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].itemOptionDetailId").type(JsonFieldType.NUMBER).description("상품 옵션 상세 PK"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].name").type(JsonFieldType.STRING).description("상품 옵션명"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].addPrice").type(JsonFieldType.NUMBER).description("상품 옵션 추가 금액"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].stockQuantity").type(JsonFieldType.NUMBER).description("상품 옵션 재고 수량"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].registrableData").type(JsonFieldType.NUMBER).description("").ignored(),
                                fieldWithPath("itemThumbnails").type(JsonFieldType.ARRAY).description("상품 썸네일").optional(),
                                fieldWithPath("itemThumbnails[].itemThumbnailId").type(JsonFieldType.NUMBER).description("상품 썸네일 PK").optional(),
                                fieldWithPath("itemThumbnails[].displayOrder").type(JsonFieldType.NUMBER).description("상품 썸네일 표시 순서").optional(),
                                fieldWithPath("itemThumbnails[].file").type(JsonFieldType.STRING).description("상품 썸네일 이미지 파일").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
    }

    @DisplayName("아이템 수정 (파일)")
    @Test
    void updateItemWithFile() throws Exception {
        //given
        MockMultipartFile itemMainImageFile = new MockMultipartFile(
                "file",         // 파일 파라미터 이름
                "example.txt",               // 원본 파일 이름
                "text/plain",                // 파일 타입 (MIME 타입)
                "This is the file content".getBytes()  // 파일 내용을 바이트 배열로 지정
        );

        MockMultipartFile itemThumbnailImageFile = new MockMultipartFile(
                "itemThumbnails[0].file",         // 파일 파라미터 이름
                "example111.txt",               // 원본 파일 이름
                "text/plain",                // 파일 타입 (MIME 타입)
                "This is the file content".getBytes()  // 파일 내용을 바이트 배열로 지정
        );

        AdminItemRequestDto requestDto = generateAdminItemRequestDto();

        //when

        //then
        this.mockMvc.perform(multipart("/api/admin/items/{itemId}", 1L)
                        .file(itemMainImageFile)
                        .file(itemThumbnailImageFile)
                        .with(user(generateAdminSecurityMemberDto()))
                        .contentType("multipart/form-data")
                        .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andDo(document("admin_update_item",
                        requestParts(
                                partWithName("file").description("상품 메인 이미지"),
                                partWithName("itemThumbnails[0].file").description("상품 썸네일 이미지")
                        ),
                        requestFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 PK"),
                                fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("브랜드 PK"),
                                fieldWithPath("itemCategoryId").type(JsonFieldType.NUMBER).description("상품 카테고리 PK"),
                                fieldWithPath("itemNameEn").type(JsonFieldType.STRING).description("상품명 영문"),
                                fieldWithPath("itemNameKo").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("targetType").type(JsonFieldType.STRING).description("타겟 타입: " + Arrays.stream(TargetType.values()).map(TargetType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("totalStockQuantity").type(JsonFieldType.NUMBER).description("상품 총 재고 수량"),
                                fieldWithPath("file").type(JsonFieldType.STRING).description("상품 메인 이미지").optional(),
                                fieldWithPath("availableData").type(JsonFieldType.STRING).description("").ignored(),
                                fieldWithPath("itemOptions").type(JsonFieldType.ARRAY).description("상품 옵션"),
                                fieldWithPath("itemOptions[].itemOptionId").type(JsonFieldType.NUMBER).description("상품 옵션"),
                                fieldWithPath("itemOptions[].itemOptionType").type(JsonFieldType.STRING).description("상품 옵션 타입: " + Arrays.stream(ItemOptionType.values()).map(ItemOptionType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("itemOptions[].displayOrder").type(JsonFieldType.NUMBER).description("상품 옵션 표시 순서"),
                                fieldWithPath("itemOptions[].registrableData").type(JsonFieldType.NUMBER).description("").ignored(),
                                fieldWithPath("itemOptions[].itemOptionDetails").type(JsonFieldType.ARRAY).description("상품 옵션"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].itemOptionDetailId").type(JsonFieldType.NUMBER).description("상품 옵션 상세 PK"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].name").type(JsonFieldType.STRING).description("상품 옵션명"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].addPrice").type(JsonFieldType.NUMBER).description("상품 옵션 추가 금액"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].stockQuantity").type(JsonFieldType.NUMBER).description("상품 옵션 재고 수량"),
                                fieldWithPath("itemOptions[].itemOptionDetails[].registrableData").type(JsonFieldType.NUMBER).description("").ignored(),
                                fieldWithPath("itemThumbnails").type(JsonFieldType.ARRAY).description("상품 썸네일").optional(),
                                fieldWithPath("itemThumbnails[].itemThumbnailId").type(JsonFieldType.NUMBER).description("상품 썸네일 PK").optional(),
                                fieldWithPath("itemThumbnails[].displayOrder").type(JsonFieldType.NUMBER).description("상품 썸네일 표시 순서").optional(),
                                fieldWithPath("itemThumbnails[].file").type(JsonFieldType.STRING).description("상품 썸네일 이미지 파일").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
    }

    @DisplayName("아이템 상세 덮어쓰기")
    @Test
    void putItemDetail() throws Exception {
        //given
        AdminItemDetailRequestDto requestDto = generateAdminItemDetailRequestDto();

        //when

        //then
        this.mockMvc.perform(put("/api/admin/items/{itemId}/detail", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                ).andExpect(status().isOk())
                .andDo(document("admin_put_item_detail",
                        pathParameters(
                                parameterWithName("itemId").description("상품 PK")
                        ),
                        requestFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 PK"),
                                fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("브랜드 PK"),
                                fieldWithPath("itemDetailId").type(JsonFieldType.NUMBER).description("상품 상세 PK"),
                                fieldWithPath("itemNumber").type(JsonFieldType.STRING).description("품목 번호").optional(),
                                fieldWithPath("materialName").type(JsonFieldType.STRING).description("제품 소재 (면 100% 등)"),
                                fieldWithPath("manufacturer").type(JsonFieldType.STRING).description("제조사"),
                                fieldWithPath("manufacturerCountryName").type(JsonFieldType.STRING).description("제조국"),
                                fieldWithPath("manufacturingDate").type(JsonFieldType.STRING).description("제조연월"),
                                fieldWithPath("warrantyPeriod").type(JsonFieldType.STRING).description("품질보증기간"),
                                fieldWithPath("availableData").type(JsonFieldType.STRING).description("").ignored()

                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));;
    }

    @DisplayName("상품 에디터 정보 등록")
    @Test
    void insertItemEditorInfo() throws Exception {
        //given
        MockMultipartFile itemEditorContentImageFile = new MockMultipartFile(
                "itemEditorContents[0].itemEditorImage.file",         // 파일 파라미터 이름
                "example.txt",               // 원본 파일 이름
                "text/plain",                // 파일 타입 (MIME 타입)
                "This is the file content".getBytes()  // 파일 내용을 바이트 배열로 지정
        );


        AdminItemEditorInfoRequestDto requestDto = generateAdminItemEditorInfoRequestDto();

        //when

        //then
        this.mockMvc.perform(multipart("/api/admin/items/{itemId}/editor-infos", 1L)
                        .file(itemEditorContentImageFile)
                        .with(user(generateAdminSecurityMemberDto()))
                        .contentType("multipart/form-data")
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("admin_insert_item_editor_info",
                        pathParameters(
                                parameterWithName("itemId").description("상품 PK")
                        ),
                        requestParts(
                                partWithName("itemEditorContents[0].itemEditorImage.file").description("상품 메인 이미지")
                        ),
                        requestFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 PK"),
                                fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("브랜드 PK"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("상품 에디터 정보 제목"),
                                fieldWithPath("isMain").type(JsonFieldType.BOOLEAN).description("메인 에디터 여부").optional(),
                                fieldWithPath("availableData").ignored(),
                                fieldWithPath("itemEditorContents").type(JsonFieldType.ARRAY).description("아이템 에디터 내용"),
                                fieldWithPath("itemEditorContents[].id").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 PK"),
                                fieldWithPath("itemEditorContents[].type").type(JsonFieldType.STRING).description("상품 에디터 내용 타입: " + Arrays.stream(ItemEditorContentType.values()).map(ItemEditorContentType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("itemEditorContents[].displayOrder").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 노출 순서"),
                                fieldWithPath("itemEditorContents[].itemEditorMarkupText").type(JsonFieldType.OBJECT).description("아이템 에디터 내용 - 마크업 텍스트").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorMarkupText.id").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 - 마크업 텍스트 PK").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorMarkupText.text").type(JsonFieldType.STRING).description("아이템 에디터 내용 - 마크업 텍스트 내용").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorImage").type(JsonFieldType.OBJECT).description("아이템 에디터 내용 - 이미지").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorImage.id").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 - 이미지 PK").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorImage.file").type(JsonFieldType.STRING).description("아이템 에디터 내용 - 이미지 파일").optional(),
                                fieldWithPath("itemEditorContents[].availableData").ignored()

                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
    }

    @DisplayName("상품 에디터 정보 수정")
    @Test
    void updateItemEditorInfo() throws Exception {
        //given
        MockMultipartFile itemEditorContentImageFile = new MockMultipartFile(
                "itemEditorContents[0].itemEditorImage.file",         // 파일 파라미터 이름
                "example.txt",               // 원본 파일 이름
                "text/plain",                // 파일 타입 (MIME 타입)
                "This is the file content".getBytes()  // 파일 내용을 바이트 배열로 지정
        );


        AdminItemEditorInfoRequestDto requestDto = generateAdminItemEditorInfoRequestDto();

        //when

        //then
        this.mockMvc.perform(multipart("/api/admin/items/{itemId}/editor-infos/{itemEditorInfoId}", 1L, 1L)
                        .file(itemEditorContentImageFile)
                        .with(user(generateAdminSecurityMemberDto()))
                        .contentType("multipart/form-data")
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("admin_update_item_editor_info",
                        pathParameters(
                                parameterWithName("itemId").description("상품 PK"),
                                parameterWithName("itemEditorInfoId").description("상품 에디터 정보 PK")
                        ),
                        requestParts(
                                partWithName("itemEditorContents[0].itemEditorImage.file").description("상품 메인 이미지")
                        ),
                        requestFields(
                                fieldWithPath("companyId").type(JsonFieldType.NUMBER).description("회사 PK"),
                                fieldWithPath("brandId").type(JsonFieldType.NUMBER).description("브랜드 PK"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("상품 에디터 정보 제목"),
                                fieldWithPath("isMain").type(JsonFieldType.BOOLEAN).description("메인 에디터 여부").optional(),
                                fieldWithPath("availableData").ignored(),
                                fieldWithPath("itemEditorContents").type(JsonFieldType.ARRAY).description("아이템 에디터 내용"),
                                fieldWithPath("itemEditorContents[].id").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 PK"),
                                fieldWithPath("itemEditorContents[].type").type(JsonFieldType.STRING).description("상품 에디터 내용 타입: " + Arrays.stream(ItemEditorContentType.values()).map(ItemEditorContentType::name).collect(Collectors.joining(" | "))).optional(),
                                fieldWithPath("itemEditorContents[].displayOrder").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 노출 순서"),
                                fieldWithPath("itemEditorContents[].itemEditorMarkupText").type(JsonFieldType.OBJECT).description("아이템 에디터 내용 - 마크업 텍스트").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorMarkupText.id").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 - 마크업 텍스트 PK").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorMarkupText.text").type(JsonFieldType.STRING).description("아이템 에디터 내용 - 마크업 텍스트 내용").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorImage").type(JsonFieldType.OBJECT).description("아이템 에디터 내용 - 이미지").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorImage.id").type(JsonFieldType.NUMBER).description("아이템 에디터 내용 - 이미지 PK").optional(),
                                fieldWithPath("itemEditorContents[].itemEditorImage.file").type(JsonFieldType.STRING).description("아이템 에디터 내용 - 이미지 파일").optional(),
                                fieldWithPath("itemEditorContents[].availableData").ignored()

                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
    }

    @DisplayName("아이템 에디터 정보 삭제")
    @Test
    void deleteItemEditorInfoByIds() throws Exception {
        //given

        //when

        //then
        this.mockMvc.perform(delete("/api/admin/items/{itemId}/editor-infos", 1L)
                        .with(user(generateAdminSecurityMemberDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(1L, 2L))))
                .andExpect(status().isOk())
                .andDo(document("admin_delete_item_editor_info",
                        pathParameters(
                                parameterWithName("itemId").description("상품 PK")
                        ),
                        requestFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("itemEditorInfoIds")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
    }

    @DisplayName("아이템 에디터 컨텐츠 삭제")
    @Test
    void deleteItemEditorContent() throws Exception {
        //given

        //when

        //then
        this.mockMvc.perform(delete("/api/admin/items/{itemId}/editor-infos/{itemEditorInfoId}/contents", 1L, 1L)
                        .with(user(generateAdminSecurityMemberDto()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(1L, 2L))))
                .andExpect(status().isOk())
                .andDo(document("admin_delete_item_editor_content",
                        pathParameters(
                                parameterWithName("itemId").description("상품 PK"),
                                parameterWithName("itemEditorInfoId").description("상품 에디터 정보 PK")
                        ),
                        requestFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("itemEditorContentIds")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
    }

    private AdminItemEditorInfoRequestDto generateAdminItemEditorInfoRequestDto() {
        AdminItemEditorImageRequestDto image = new AdminItemEditorImageRequestDto();
        image.setId(1L);
        image.setFile(null);

        AdminItemEditorMarkupTextRequestDto markupText = new AdminItemEditorMarkupTextRequestDto();
        markupText.setId(1L);
        markupText.setText("이거 멋진 반팔티예요.");

        AdminItemEditorContentRequestDto content1 = new AdminItemEditorContentRequestDto();
        content1.setId(1L);
        content1.setType(ItemEditorContentType.IMAGE);
        content1.setDisplayOrder(1);
        content1.setItemEditorImage(image);
        AdminItemEditorContentRequestDto content2 = new AdminItemEditorContentRequestDto();
        content2.setId(2L);
        content2.setType(ItemEditorContentType.MARKUP_TEXT);
        content2.setDisplayOrder(2);
        content2.setItemEditorMarkupText(markupText);

        AdminItemEditorInfoRequestDto requestDto = new AdminItemEditorInfoRequestDto();
        requestDto.setCompanyId(1L);
        requestDto.setBrandId(1L);
        requestDto.setTitle("폴햄 반팔 에디터 정보");
        requestDto.setIsMain(true);
        requestDto.setItemEditorContents(
                List.of(
                        content1,
                        content2
                )
        );

        return requestDto;
    }

    private AdminItemDetailRequestDto generateAdminItemDetailRequestDto() {
        AdminItemDetailRequestDto adminItemDetailRequestDto = new AdminItemDetailRequestDto();
        adminItemDetailRequestDto.setCompanyId(1L);
        adminItemDetailRequestDto.setBrandId(1L);
        adminItemDetailRequestDto.setItemDetailId(1L);
        adminItemDetailRequestDto.setItemNumber("abc-12312321");
        adminItemDetailRequestDto.setMaterialName("면 100%");
        adminItemDetailRequestDto.setManufacturer("제조사 입니다.");
        adminItemDetailRequestDto.setManufacturerCountryName("한국");
        adminItemDetailRequestDto.setManufacturingDate(LocalDateTime.now());
        adminItemDetailRequestDto.setWarrantyPeriod("1년");

        return adminItemDetailRequestDto;
    }

    private AdminItemRequestDto generateAdminItemRequestDto() {
        AdminItemOptionDetailRequestDto itemOptionDetail = new AdminItemOptionDetailRequestDto();
        itemOptionDetail.setItemOptionDetailId(1L);
        itemOptionDetail.setName("S");
        itemOptionDetail.setAddPrice(0L);
        itemOptionDetail.setStockQuantity(5);

        AdminItemOptionRequestDto itemOption = new AdminItemOptionRequestDto();
        itemOption.setItemOptionId(1L);
        itemOption.setItemOptionType(ItemOptionType.SIZE);
        itemOption.setDisplayOrder(1);
        itemOption.setItemOptionDetails(List.of(itemOptionDetail));

        AdminItemThumbnailImageRequestDto imageThumbnail = new AdminItemThumbnailImageRequestDto();
        imageThumbnail.setItemThumbnailId(1L);
        imageThumbnail.setDisplayOrder(1);
        imageThumbnail.setFile(null);

        AdminItemRequestDto adminItemRequestDto = new AdminItemRequestDto();
        adminItemRequestDto.setCompanyId(1L);
        adminItemRequestDto.setBrandId(1L);
        adminItemRequestDto.setItemCategoryId(1L);
        adminItemRequestDto.setItemNameEn("폴햄 반팔 티셔츠");
        adminItemRequestDto.setItemNameKo("폴햄 반팔 티셔츠");
        adminItemRequestDto.setPrice(39000L);
        adminItemRequestDto.setTargetType(TargetType.MALE);
        adminItemRequestDto.setTotalStockQuantity(15);
        adminItemRequestDto.setFile(null);
        adminItemRequestDto.setItemOptions(List.of(itemOption));
        adminItemRequestDto.setItemThumbnails(List.of(imageThumbnail));

        return adminItemRequestDto;
    }
}