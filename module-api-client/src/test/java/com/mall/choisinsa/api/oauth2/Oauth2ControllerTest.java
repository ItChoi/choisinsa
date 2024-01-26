package com.mall.choisinsa.api.oauth2;

import com.mall.choisinsa.ClientApplicationBaseTest;
import com.mall.choisinsa.enumeration.SnsType;
import core.dto.client.response.oauth2.Oauth2LoginResponseDto;
import core.dto.client.response.oauth2.Oauth2ResponseDto;
import core.dto.client.response.oauth2.Oauth2UserResponseDto;
import core.service.oauth2.Oauth2Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Oauth2Controller.class)
public class Oauth2ControllerTest extends ClientApplicationBaseTest {

    @MockBean
    private Oauth2Service oauth2Service;

    @DisplayName("oauth 로그인")
    @Test
    void oauthLogin() throws Exception {
        //given
        Oauth2LoginResponseDto oauth2LoginResponseDto = generateOauth2LoginResponseDto();


        //when
        given(oauth2Service.login(any(), any()))
                .willReturn(oauth2LoginResponseDto);

        //then
        this.mockMvc.perform(get("/login/oauth2/code/{loginType}", SnsType.INSTAGRAM)
                        .param("code", "aaa")
                        .param("state", "200")
                        .param("error", "")
                        .param("error_description", ""))
                .andExpect(status().isOk())
                .andDo(document("member_oauth_login",
                        pathParameters(
                                parameterWithName("loginType").description("sns type")
                        ),
                        requestParameters(
                                parameterWithName("code").description("요청 코드"),
                                parameterWithName("state").description("요청 state"),
                                parameterWithName("error").description("요청 에러"),
                                parameterWithName("error_description").description("요청 에러 상세")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorType").description("예외 타입"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data.jwtAccessToken").type(JsonFieldType.STRING).description("jwt token"),
                                fieldWithPath("data.oauth2AcessToken").type(JsonFieldType.STRING).description("oauth access token"),
                                fieldWithPath("data.snsType").type(JsonFieldType.STRING).description("sns type: " + Arrays.stream(SnsType.values()).map(SnsType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("data.oauth2UserInfo").type(JsonFieldType.OBJECT).description("oauth 유저 정보"),
                                fieldWithPath("data.oauth2UserInfo.isAlreadyConnectSns").type(JsonFieldType.BOOLEAN).description("이미 sns 로그인 연동했는지 여부"),
                                fieldWithPath("data.oauth2UserInfo.isAlreadyExistEmail").type(JsonFieldType.BOOLEAN).description("이미 존재하는 이메일 여부"),
                                fieldWithPath("data.oauth2UserInfo.memberInfo").type(JsonFieldType.OBJECT).description("회원 정보"),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.id").type(JsonFieldType.STRING).description("sns 회원 PK"),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.nickname").type(JsonFieldType.STRING).description("sns 회원 닉네임").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.profileImageUrl").type(JsonFieldType.STRING).description("sns 회원 프로필 이미지").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.name").type(JsonFieldType.STRING).description("sns 회원 이름").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.email").type(JsonFieldType.STRING).description("sns 회원 이메일").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.gender").type(JsonFieldType.STRING).description("sns 회원 성별").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.birthyear").type(JsonFieldType.STRING).description("sns 회원 생일 - 연도").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.birthday").type(JsonFieldType.STRING).description("sns 회원 생일 - 일자").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.phoneNumber").type(JsonFieldType.STRING).description("sns 회원 휴대폰 번호").optional(),
                                fieldWithPath("data.oauth2UserInfo.memberInfo.snsType").type(JsonFieldType.STRING).description("sns type: " + Arrays.stream(SnsType.values()).map(SnsType::name).collect(Collectors.joining(" | "))).optional()
                        )
                ));
    }

    private static Oauth2LoginResponseDto generateOauth2LoginResponseDto() {
        return new Oauth2LoginResponseDto(
                "token value",
                "oauth token",
                SnsType.INSTAGRAM,
                new Oauth2ResponseDto(
                        true,
                        true,
                        new Oauth2UserResponseDto(
                                "abc12312313",
                                "test",
                                null,
                                "test@test.com",
                                "남성",
                                SnsType.INSTAGRAM
                        )
                )
        );
    }
}