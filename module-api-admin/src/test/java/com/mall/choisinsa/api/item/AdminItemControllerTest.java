package com.mall.choisinsa.api.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.AdminApplicationBaseTest;
import core.service.item.AdminItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminItemController.class)
class AdminItemControllerTest extends AdminApplicationBaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminItemService adminItemService;

    @DisplayName("아이템 등록 (파일 ")
    @Test
    void insertItemWithFile() throws Exception {
        /*this.mockMvc.perform(
                        post("/api/admin/items")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("insert_item_with_file",
                        requestParameters(

                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("true: 추천 가능, false: 추천 불가능")
                        )
                ));*/

    }



}