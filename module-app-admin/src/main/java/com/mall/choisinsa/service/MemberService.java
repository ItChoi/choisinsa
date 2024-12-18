package com.mall.choisinsa.service;

import core.common.redis.RedisKeyGenerator;
import com.mall.choisinsa.dto.LoginUserDto;
import com.mall.choisinsa.dto.SecurityMemberDto;
import core.common.enumeration.SnsType;
import core.common.enumeration.member.MemberStatus;
import core.common.enumeration.member.MemberType;
import com.mall.choisinsa.web.provider.JwtTokenProvider;
import core.common.exception.ErrorType;
import core.common.exception.ErrorTypeAdviceException;
import core.common.validator.MemberValidator;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import core.domain.member.MemberSnsConnect;
import core.dto.client.request.member.MemberLoginRequestDto;
import core.dto.client.request.member.MemberRegisterRequestDto;
import core.dto.client.request.member.MemberRegisterRequestDto.MemberDetailRegisterRequestDto;
import core.dto.client.request.member.MemberSnsConnectRegisterRequestDto;
import core.dto.client.request.member.SnsMemberRegisterRequestDto;
import core.dto.client.response.member.MemberResponseDto;
import core.dto.general.JwtTokenDto;
import core.dto.general.ReissueTokenDto;
import core.repository.member.MemberDetailRepository;
import core.repository.member.MemberRepository;
import core.service.event.EventService;
import core.service.member.CoreMemberService;
import core.service.member.MemberSnsConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EventService eventService;
    private final MemberDetailRepository memberDetailRepository;
    private final MemberSnsConnectService memberSnsConnectService;

    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final CoreMemberService coreMemberService;

    @Transactional(readOnly = true)
    public boolean canRecommendByLoginId(String loginId) {
        Member member = findByLoginId(loginId);

        if (!isFormalMember(member)) {
            return false;
        }

        return eventService.canRecommendByMemberId(member.getId());
    }

    @Transactional(readOnly = true)
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElse(null);
    }

    public static boolean isFormalMember(Member member) {
        return member != null && member.getStatus() == MemberStatus.NORMAL;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberResponseDtoById(Long loggedInMemberId,
                                                       Long memberId) {
        if (loggedInMemberId == null || memberId == null || !loggedInMemberId.equals(memberId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_AUTHORITY);
        }

        return findMemberResponseDtoById(memberId);
    }

    private MemberResponseDto findMemberResponseDtoById(Long memberId) {
        Member member = findById(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));

        MemberDetail memberDetail = memberDetailRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));

        return new MemberResponseDto(member, memberDetail);
    }

    @Transactional(readOnly = true)
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public void saveMember(MemberRegisterRequestDto requestDto) {
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        MemberValidator.validateRegisterOrThrow(requestDto.getLoginId(), password, email);

        if (isExistEmail(email)) {
            throw new ErrorTypeAdviceException(ErrorType.ALREADY_EXISTS_DATA, "이메일");
        }

        String encodePassword = encodePassword(password);
        Member savedMember = memberRepository.save(requestDto.toMember(encodePassword));
        Long memberId = savedMember.getId();
        memberDetailRepository.save(toMemberDetailBy(memberId, requestDto.getMemberDetail()));

        MemberSnsConnectRegisterRequestDto memberSnsConnectRegisterInfo = requestDto.getMemberSnsConnectRegisterInfo();
        if (memberSnsConnectRegisterInfo != null) {
            memberSnsConnectService.saveMemberSnsConnect(memberId, memberSnsConnectRegisterInfo);
        }
    }

    private MemberDetail toMemberDetailBy(Long memberId,
                                          MemberDetailRegisterRequestDto memberDetail) {

        if (memberId == null || memberDetail == null) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        String recommenderLoginId = memberDetail.getRecommenderLoginId();
        Long recommenderMemberId = null;
        if (StringUtils.hasText(recommenderLoginId)) {
            Member recommenderMember = memberRepository.findByLoginId(recommenderLoginId)
                    .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));
            recommenderMemberId = recommenderMember.getId();
        }

        return memberDetail.toMemberDetail(memberId, recommenderMemberId);
    }

    @Transactional(readOnly = true)
    public boolean isExistEmail(String email) {
        if (StringUtils.hasText(email)) {
            log.warn("[WARNNING]: {}", "이메일 존재 여부를 체크 할 수 없습니다.");
            return false;
        }
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public String saveMemberWithOauth2(SnsType snsType,
                                       SnsMemberRegisterRequestDto requestDto) {
        String snsId = requestDto.getSnsId();

        return null;
    }

    @Transactional(readOnly = true)
    public boolean isExistLoginId(String loginId) {
        if (!StringUtils.hasText(loginId)) {
            log.warn("[WARNNING]: {}", "이메일 존재 여부를 체크 할 수 없습니다.");
            return false;
        }
        return memberRepository.existsByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public MemberSnsConnect findSnsMemberBySnsIdAndSnsType(String snsId,
                                                           SnsType snsType) {
        return memberSnsConnectService.findBySnsIdAndSnsType(snsId, snsType)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Member findByIdOrThrow(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public boolean existsBySnsIdAndSnsType(String snsId,
                                           SnsType snsType) {

        return memberSnsConnectService.existsBySnsIdAndSnsType(snsId, snsType);
    }

    @Transactional
    public void connectAccountBy(Long memberId,
                                 MemberSnsConnectRegisterRequestDto requestDto) {
        validateAvailableMember(memberId);
        memberSnsConnectService.saveMemberSnsConnect(memberId, requestDto);
    }

    private void validateAvailableMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));

        if (member.getStatus() != MemberStatus.NORMAL) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_DATA, "회원 상태");
        }
    }

    @Transactional(readOnly = true)
    public JwtTokenDto login(MemberLoginRequestDto requestDto) {
        JwtTokenDto jwtTokenDto = login(requestDto.getMemberType(), requestDto.getLoginId(), requestDto.getPassword());

        coreMemberService.setInMemory(
                RedisKeyGenerator.jwtRefreshToken(requestDto.getLoginId()),
                jwtTokenDto.getRefreshToken()
        );

        return jwtTokenDto;
    }

    public String refreshAccessToken(String loginId,
                                     ReissueTokenDto requestDto) {
        return reissueAccessToken(
                coreMemberService.getInMemory(RedisKeyGenerator.jwtRefreshToken(loginId)),
                requestDto
        );
    }

    public JwtTokenDto login(MemberType memberType,
                             String loginId,
                             String password) {

        validateLoginInfoOrThrowException(memberType, loginId, password);

        Authentication authenticate = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, password)
        );
        validateLoginInfoAfterAuth(memberType, authenticate);
        return jwtTokenProvider.createJwtToken(authenticate);
    }

    private void validateLoginInfoAfterAuth(MemberType memberType,
                                            Authentication authenticate) {
        Object principal = authenticate.getPrincipal();
        if (!(principal instanceof SecurityMemberDto)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_SUPPORT_AUTHENTICATION);
        }

        SecurityMemberDto memberDto = (SecurityMemberDto) principal;
        if (memberType == null || memberType != memberDto.getMemberType()) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_AUTHORITY);
        }
    }

    public JwtTokenDto loginWithSns(SnsType snsType,
                                             String snsId) {

        MemberSnsConnect memberSnsConnect = memberSnsConnectService.findBySnsTypeAndSnsIdOrThrow(snsType, snsId);
        Member securityMember = findByIdOrThrow(memberSnsConnect.getMemberId());
        return login(MemberType.MEMBER, securityMember.getLoginId(), securityMember.getPassword());
    }

    private void validateLoginInfoOrThrowException(MemberType memberType,
                                                   String loginId,
                                                   String password) {
        if (memberType == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        MemberValidator.validateLoginOrThrow(loginId, password);
    }

    public String encodePassword(String plainText) {
        if (!StringUtils.hasText(plainText)) {
            return null;
        }
        return passwordEncoder.encode(plainText);
    }

    public LoginUserDto getLoginUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_LOGGED_IN);
        }

        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_LOGGED_IN);
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            if (authentication.getPrincipal() instanceof SecurityMemberDto) {
                return new LoginUserDto((SecurityMemberDto) authentication.getPrincipal());
            }
        }

        throw new ErrorTypeAdviceException(ErrorType.NOT_SUPPORT_AUTHENTICATION);
    }

    public String reissueAccessToken(String refreshToken,
                                     ReissueTokenDto reissueTokenDto) {
        validateJwtToken(refreshToken, reissueTokenDto);
        if (jwtTokenProvider.isExpiredJwtToken(reissueTokenDto.getExpiredAccessToken())) {
            if (jwtTokenProvider.isValidRefreshToken(refreshToken)) {
                return jwtTokenProvider.createToken(refreshToken);
            }
        }

        throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_REISSUE_TOKEN);
    }

    private void validateJwtToken(String refreshToken,
                                  ReissueTokenDto reissueTokenDto) {
        if (!StringUtils.hasText(refreshToken) || reissueTokenDto == null || !StringUtils.hasText(reissueTokenDto.getExpiredAccessToken())) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }
    }

}
