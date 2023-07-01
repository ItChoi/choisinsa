package core.service.member;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.member.MemberSnsConnect;
import core.repository.member.MemberSnsConnectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSnsConnectService {

    private final MemberSnsConnectRepository memberSnsConnectRepository;

    @Transactional
    public void saveMemberSnsConnect(Long memberId,
                                     SnsType snsType) {
        if (memberSnsConnectRepository.existsByMemberIdAndSnsType(memberId, snsType)) {
            throw new ErrorTypeAdviceException(ErrorType.ALREADY_EXISTS_DATA, "이미" + snsType.getDesc() + "연동이 됐습니다.");
        }

        memberSnsConnectRepository.save(
                MemberSnsConnect.builder()
                        .memberId(memberId)
                        .snsType(snsType)
                        .build());

    }

    @Transactional(readOnly = true)
    public Optional<MemberSnsConnect> findBySnsIdAndSnsType(String snsId,
                                                            SnsType snsType) {
        return memberSnsConnectRepository.findBySnsIdAndSnsType(snsId, snsType);
    }
}
