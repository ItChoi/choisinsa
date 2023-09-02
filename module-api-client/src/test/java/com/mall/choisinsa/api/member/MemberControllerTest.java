package com.mall.choisinsa.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.ClientApplicationBaseTest;
import com.mall.choisinsa.enumeration.member.MemberType;
import com.mall.choisinsa.security.service.SecurityMemberService;
import core.dto.client.request.member.MemberLoginRequestDto;
import core.dto.client.response.member.MemberLoginResponseDto;
import core.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends ClientApplicationBaseTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private SecurityMemberService securityMemberService;

    @DisplayName("로그인")
    @Test
    void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto();
        requestDto.setMemberType(MemberType.MEMBER);
        requestDto.setLoginId("member");
        requestDto.setPassword("1234");
        //when
        given(securityMemberService.login(any(), any(), any()))
                .willReturn("bearer token value");

        this.mockMvc.perform(post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("member_login",
                        requestFields(
                                fieldWithPath("memberType").type(JsonFieldType.STRING).description("회원 타입: " + Arrays.stream(MemberType.values())),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("bearer token")
                        )
                ));


        //then
    }

    @DisplayName("추천인 가능 여부 - 로그인 아이디")
    @Test
    void canRecommendByLoginId() throws Exception {
        given(memberService.canRecommendByLoginId(any()))
                .willReturn(true);
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