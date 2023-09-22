package com.mall.choisinsa.api.member;

import com.mall.choisinsa.ClientApplicationBaseTest;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.member.GenderType;
import com.mall.choisinsa.enumeration.member.MemberStatus;
import com.mall.choisinsa.enumeration.member.MemberType;
import core.dto.client.request.member.MemberLoginRequestDto;
import core.dto.client.request.member.MemberRegisterRequestDto;
import core.dto.client.request.member.MemberSnsConnectRegisterRequestDto;
import core.dto.client.response.member.MemberDetailResponseDto;
import core.dto.client.response.member.MemberResponseDto;
import core.service.member.MemberService;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class MemberControllerTest extends ClientApplicationBaseTest {

    @MockBean
    private MemberService memberService;

    @DisplayName("로그인")
    @Test
    void login() throws Exception {
        //given
        MemberLoginRequestDto requestDto = new MemberLoginRequestDto();
        requestDto.setMemberType(MemberType.MEMBER);
        requestDto.setLoginId("member");
        requestDto.setPassword("qwe123!");

        //when
        given(memberService.login(any()))
                .willReturn("bearer token value");

        //then
        this.mockMvc.perform(post("/api/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("member_login",
                        requestFields(
                                fieldWithPath("memberType").type(JsonFieldType.STRING).description("회원 타입: " + Arrays.stream(MemberType.values()).map(MemberType::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data.token").type(JsonFieldType.STRING).description("bearer token")
                        )
                ));
    }

    @DisplayName("나의 정보 보기")
    @Test
    void getMember() throws Exception {
        //given

        //when
        given(memberService.findMemberResponseDtoById(any(), any()))
                .willReturn(generateMemberResponseDto());

        //then
        this.mockMvc.perform(get("/api/members/{memberId}", 1L)
                        .with(user(generateMemberSecurityMemberDto())))
                .andExpect(status().isOk())
                .andDo(document("member_my_information",
                        pathParameters(
                                parameterWithName("memberId").description("회원 PK")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원 PK"),
                                fieldWithPath("data.loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("data.status").type(JsonFieldType.STRING).description("회원 상태: " + Arrays.stream(MemberStatus.values()).map(MemberStatus::name).collect(Collectors.joining(" | "))),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름").optional(),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일").optional(),
                                fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("닉네임").optional(),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("휴대폰 번호").optional(),
                                fieldWithPath("data.profileFileUrl").type(JsonFieldType.STRING).description("프로밀 이미지 리모트 경로").optional(),
                                fieldWithPath("data.memberDetail").type(JsonFieldType.OBJECT).description("회원 상세 객체"),
                                fieldWithPath("data.memberDetail.id").type(JsonFieldType.NUMBER).description("회원 상세 PK"),
                                fieldWithPath("data.memberDetail.birthday").type(JsonFieldType.STRING).description("생년월일").optional(),
                                fieldWithPath("data.memberDetail.gender").type(JsonFieldType.STRING).description("성별").optional(),
                                fieldWithPath("data.memberDetail.height").type(JsonFieldType.STRING).description("키").optional(),
                                fieldWithPath("data.memberDetail.weight").type(JsonFieldType.STRING).description("몸무게").optional(),
                                fieldWithPath("data.memberDetail.isAcceptMarketing").type(JsonFieldType.BOOLEAN).description("마케팅 동의 여부"),
                                fieldWithPath("data.memberDetail.isAuthenticateEmail").type(JsonFieldType.BOOLEAN).description("이메일 인증 여부"),
                                fieldWithPath("data.memberDetail.isAuthenticatePhone").type(JsonFieldType.BOOLEAN).description("휴대폰 인증 여부"),
                                fieldWithPath("data.memberDetail.selfIntroduce").type(JsonFieldType.STRING).description("자기소개").optional(),
                                fieldWithPath("data.memberDetail.recommenderMemberId").type(JsonFieldType.NUMBER).description("추천인 회원 PK").optional()
                        )
                ));
    }

    @DisplayName("회원 등록")
    @Test
    void postMember() throws Exception {
        //given
        MemberRegisterRequestDto requestDto = generateMemberRegisterRequestDto();

        //when

        //then
        this.mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("member_insert",
                        requestFields(
                                fieldWithPath("loginId").type(JsonFieldType.STRING).description("로그인 아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("memberDetail.recommenderLoginId").type(JsonFieldType.STRING).description("추천인 로그인 아이디").optional(),
                                fieldWithPath("memberDetail.isAcceptMarketing").type(JsonFieldType.BOOLEAN).description("마케팅 동의 여부"),
                                fieldWithPath("memberDetail.isAuthenticateEmail").type(JsonFieldType.BOOLEAN).description("이메일 인증 여부"),
                                fieldWithPath("memberDetail.isAuthenticatePhone").type(JsonFieldType.BOOLEAN).description("휴대폰 이증 여부"),
                                fieldWithPath("memberSnsConnectRegisterInfo").type(JsonFieldType.OBJECT).description("sns 객체").optional(),
                                fieldWithPath("memberSnsConnectRegisterInfo.snsId").type(JsonFieldType.STRING).description("sns 고유 id").optional(),
                                fieldWithPath("memberSnsConnectRegisterInfo.snsType").type(JsonFieldType.STRING).description("sns type: " + Arrays.stream(SnsType.values()).map(SnsType::name).collect(Collectors.joining(" | "))).optional()

                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
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

    @DisplayName("sns 계정 연동")
    @Test
    void connectAccountWithSns() throws Exception {
        //given
        MemberSnsConnectRegisterRequestDto requestDto = new MemberSnsConnectRegisterRequestDto();
        requestDto.setSnsId("123123abbs213e0xxdasdsadsada");
        requestDto.setSnsType(SnsType.KAKAO);

        //when

        //then
        this.mockMvc.perform(post("/api/members/{memberId}/sns-connect", 1L)
                        .param("memberId", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(document("member_connect_sns",
                        pathParameters(
                                parameterWithName("memberId").description("회원 PK")
                        ),
                        requestFields(
                                fieldWithPath("snsId").type(JsonFieldType.STRING).description("sns 고유 id"),
                                fieldWithPath("snsType").type(JsonFieldType.STRING).description("sns type: " + Arrays.stream(SnsType.values()).map(SnsType::name).collect(Collectors.joining(" | ")))
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").description("결과 데이터")
                        )
                ));
    }

    @DisplayName("이메일 이용 가능 여부")
    @Test
    void isAvailableEmail() throws Exception {
        //given

        //when
        given(memberService.isExistEmail(any()))
                .willReturn(true);

        //then
        this.mockMvc.perform(get("/api/members/{email}/is-available", "test@test.com"))
                .andExpect(status().isOk())
                .andDo(document("is_available_email",
                        pathParameters(
                                parameterWithName("email").description("이메일")
                        ),
                        responseFields(
                                fieldWithPath("status").description("http status text"),
                                fieldWithPath("errorMsg").description("예외 메시지"),
                                fieldWithPath("data").type(JsonFieldType.BOOLEAN).description("true: 이메일 이용 가능, false: 이메일 이용 불가능")
                        )
                ));
    }

    private MemberResponseDto generateMemberResponseDto() {
        MemberDetailResponseDto memberDetailResponseDto = new MemberDetailResponseDto();
        memberDetailResponseDto.setId(1L);
        memberDetailResponseDto.setBirthday("1991-04-29");
        memberDetailResponseDto.setGender(GenderType.SECRET);
        memberDetailResponseDto.setHeight(null);
        memberDetailResponseDto.setWeight(null);
        memberDetailResponseDto.setIsAcceptMarketing(true);
        memberDetailResponseDto.setIsAuthenticateEmail(true);
        memberDetailResponseDto.setIsAuthenticatePhone(true);
        memberDetailResponseDto.setSelfIntroduce("안녕하세요 백엔드 개발자입니다.");
        memberDetailResponseDto.setRecommenderMemberId(null);

        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(1L);
        memberResponseDto.setLoginId("member");
        memberResponseDto.setStatus(MemberStatus.NORMAL);
        memberResponseDto.setName("최모씨");
        memberResponseDto.setEmail("test@test.com");
        memberResponseDto.setNickName("둘리");
        memberResponseDto.setPhoneNumber("010-0000-0000");
        memberResponseDto.setProfileFileUrl("remote file url");
        memberResponseDto.setMemberDetail(memberDetailResponseDto);

        return memberResponseDto;
    }

    private MemberRegisterRequestDto generateMemberRegisterRequestDto() {
        MemberRegisterRequestDto.MemberDetailRegisterRequestDto detailRegisterDto = new MemberRegisterRequestDto.MemberDetailRegisterRequestDto();

        detailRegisterDto.setRecommenderLoginId(null);
        detailRegisterDto.setIsAcceptMarketing(false);
        detailRegisterDto.setIsAuthenticateEmail(true);
        detailRegisterDto.setIsAuthenticatePhone(true);
        return new MemberRegisterRequestDto(
                "simpson",
                "1234",
                "test@test.com",
                detailRegisterDto,
                null
        );
    }
}
