package core.service.member;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.member.MemberStatus;
import com.mall.choisinsa.security.service.SecurityMemberService;
import com.mall.choisinsa.util.domain.MemberUtil.MemberValidator;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import core.dto.request.member.MemberRegisterRequestDto;
import core.dto.request.member.MemberRegisterRequestDto.MemberDetailRegisterRequestDto;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EventService eventService;
    private final MemberDetailRepository memberDetailRepository;
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
        MemberValidator.validateLoginOrThrow(requestDto.getLoginId(), password);

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

}
