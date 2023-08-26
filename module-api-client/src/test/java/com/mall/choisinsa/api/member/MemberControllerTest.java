package com.mall.choisinsa.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.ClientApplicationBaseTest;
import com.mall.choisinsa.security.service.SecurityMemberService;
import core.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends ClientApplicationBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private SecurityMemberService securityMemberService;

    @Test
    void test() throws Exception {
        given(memberService.canRecommendByLoginId(any())).willReturn(true);
        this.mockMvc.perform(get("/api/members/{loginId}/recommender", "test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("can_recommend_by_login_id",
                        pathParameters(
                                parameterWithName("loginId").description("추천 검증 로그인 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("true: 추천 가능, false: 추천 불가능")
                        )
                ));
    }
}