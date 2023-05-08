package core.service.member;

import com.mall.choisinsa.enumeration.SnsLoginType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.member.LoginType;
import com.mall.choisinsa.enumeration.member.MemberStatus;
import com.mall.choisinsa.security.domain.SecurityMember;
import com.mall.choisinsa.security.service.SecurityMemberService;
import com.mall.choisinsa.util.domain.MemberUtil.MemberValidator;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import core.dto.request.member.MemberRegisterRequestDto;
import core.dto.request.member.MemberRegisterRequestDto.MemberDetailRegisterRequestDto;
import core.dto.request.member.MemberSnsLinkRequestDto;
import core.dto.response.member.MemberResponseDto;
import core.repository.member.MemberDetailRepository;
import core.repository.member.MemberRepository;
import core.service.event.EventService;
import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final MemberSnsLinkService memberSnsLinkService;
    private final SecurityMemberService securityMemberService;

    @Transactional(readOnly = true)
    public boolean canRecommendByLoginId(String loginId) {
        Member member = findByLoginId(loginId);

        if (!isFormalMember(member)) {
            return false;
        }

        return eventService.canRecommendByMemberId(member.getId());
    }

    @Nullable
    @Transactional(readOnly = true)
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElse(null);
    }

    public static boolean isFormalMember(Member member) {
        return member != null && member.getStatus() == MemberStatus.NORMAL;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMemberById(Long memberId) {
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

        String encodePassword = securityMemberService.encodePassword(password);
        Member savedMember = memberRepository.save(requestDto.toMember(encodePassword));
        memberDetailRepository.save(toMemberDetailBy(savedMember.getId(), requestDto.getMemberDetail()));
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
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public String saveMemberWithOauth2(SnsLoginType loginType,
                                       MemberRegisterRequestDto requestDto) {
        /**
         * 회원가입시 이메일, sns 로그인시 이메일 - 겹칠 가능성이 있다.
         * 이럴 경우 어떻게 진행되어야 하는지 체크 필요
         *
         * 1. 사이트 회원가입과 동일한 이메일인 경우
         * 2.
         */
        return null;
    }

    @Transactional
    public void linkMemberWithSns(String email,
                                  MemberSnsLinkRequestDto requestDto) {
        SnsLoginType snsType = requestDto.getSnsType();
        if (!StringUtils.hasText(email) || snsType == null || !MemberValidator.isAvailableEmail(email)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));

        if (member.getLoginType() != LoginType.SITE) {
            throw new ErrorTypeAdviceException(ErrorType.ONLY_AVAILABLE_SERVICE_FOR_SITE);
        }

        memberSnsLinkService.saveMemberSnsLink(member.getId(), snsType);
    }
}
