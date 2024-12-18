package com.mall.choisinsa.api.member;

import com.mall.choisinsa.AdminApplicationBaseTest;
import core.common.enumeration.member.MemberType;
import com.mall.choisinsa.service.MemberService;
import core.dto.client.request.member.MemberLoginRequestDto;
import core.dto.general.JwtTokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminMemberController.class)
class AdminMemberControllerTest extends AdminApplicationBaseTest {
    @MockBean
    private MemberService memberService;

    @DisplayName("로그인")
    @Test
    void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto();
        requestDto.setMemberType(MemberType.ADMIN);
        requestDto.setLoginId("admin");
        requestDto.setPassword("qwe123!");

        //when
        given(memberService.login(any()))
                .willReturn(new JwtTokenDto("acasdsadasdsad", "asdasdasdasdas"));

        //then
        this.mockMvc.perform(post("/api/admin/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("admin_login",
                        requestFields(
                                fieldWithPath("memberType").type(JsonFieldType.STRING).description("회원 타입: " + Arrays.stream(MemberType.values()).map(MemberType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorType").description("예외 타입"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("bearer token"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("refresh token")
                        )
                ));
    }

}