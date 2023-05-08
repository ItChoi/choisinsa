package core.service.member;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsLoginType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.member.MemberSnsLink;
import core.repository.member.MemberSnsLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberSnsLinkService {
    private final MemberSnsLinkRepository memberSnsLinkRepository;


    @Transactional
    public void saveMemberSnsLink(Long memberId,
                                  SnsLoginType snsType) {
        if (memberSnsLinkRepository.existsByMemberIdAndSnsType(memberId, snsType)) {
            throw new ErrorTypeAdviceException(ErrorType.ALREADY_EXISTS_DATA, "이미" + snsType.getDesc() + "연동이 됐습니다.");
        }

        memberSnsLinkRepository.save(
                MemberSnsLink.builder()
                        .memberId(memberId)
                        .snsType(snsType)
                        .build());

    }
}
