package core.service.member;

import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.member.MemberStatus;
import com.mall.choisinsa.util.MemberUtil.MemberValidatorThrow;
import core.domain.member.Member;
import core.domain.member.MemberDetail;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import core.dto.request.member.MemberRegisterRequestDto;
import core.dto.request.member.MemberRegisterRequestDto.MemberDetailRegisterRequestDto;
import com.mall.choisinsa.dto.response.ResponseWrapper;
import core.repository.member.MemberRepository;
import core.service.event.EventService;
import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EventService eventService;
    private final MemberDetailService memberDetailService;

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

    @Nullable
    @Transactional(readOnly = true)
    public Object findMemberById(Long memberId) {
        return null;
    }

    @Transactional
    public ResponseWrapper saveMember(MemberRegisterRequestDto requestDto) {
        MemberValidatorThrow.validateMemberBy(requestDto.getLoginId(), requestDto.getPassword());

        Member savedMember = memberRepository.save(requestDto.toMember());
        MemberDetail savedMemberDetail = toMemberDetailBy(savedMember.getId(), requestDto.getMemberDetail());
        if (savedMemberDetail == null) {

        }

        return null;
    }

    private MemberDetail toMemberDetailBy(Long memberId,
                                          MemberDetailRegisterRequestDto memberDetail) {

        if (memberId == null || memberDetail == null) {
            return null;
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
