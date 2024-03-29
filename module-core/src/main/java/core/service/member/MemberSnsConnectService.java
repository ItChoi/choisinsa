package core.service.member;

import core.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import core.common.exception.ErrorType;
import core.domain.member.MemberSnsConnect;
import core.dto.client.request.member.MemberSnsConnectRegisterRequestDto;
import core.repository.member.MemberSnsConnectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSnsConnectService {

    private final MemberSnsConnectRepository memberSnsConnectRepository;

    @Transactional
    public void saveMemberSnsConnect(Long memberId,
                                     MemberSnsConnectRegisterRequestDto requestDto) {
        validateMemberSnsConnectForRegister(memberId, requestDto);
        memberSnsConnectRepository.save(
                MemberSnsConnect.builder()
                        .memberId(memberId)
                        .snsId(requestDto.getSnsId())
                        .snsType(requestDto.getSnsType())
                        .build());
    }

    @Transactional(readOnly = true)
    public Optional<MemberSnsConnect> findBySnsIdAndSnsType(String snsId,
                                                            SnsType snsType) {
        return memberSnsConnectRepository.findBySnsIdAndSnsType(snsId, snsType);
    }

    @Transactional(readOnly = true)
    public boolean existsBySnsIdAndSnsType(String snsId, SnsType snsType) {
        return memberSnsConnectRepository.existsBySnsIdAndSnsType(snsId, snsType);
    }

    @Transactional(readOnly = true)
    public Optional<MemberSnsConnect> findByMemberIdAndSnsType(Long memberId,
                                                               SnsType snsType) {
        return memberSnsConnectRepository.findByMemberIdAndSnsType(memberId, snsType);
    }

    @Transactional(readOnly = true)
    public void validateMemberSnsConnectForRegister(Long memberId,
                                                    MemberSnsConnectRegisterRequestDto requestDto) {
        String snsId = requestDto.getSnsId();
        SnsType snsType = requestDto.getSnsType();
        if (memberId == null || !StringUtils.hasText(snsId) || snsType == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        // SNS 계정이 이미 존재하는 경우
        findBySnsIdAndSnsType(snsId, snsType)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.ALREADY_EXISTS_DATA, "SNS 계정 정보"));

        // 해당 회원의 sns type이 이미 존재하는 경우
        findByMemberIdAndSnsType(memberId, snsType)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.ALREADY_EXISTS_DATA, "SNS 계정 연동"));
    }

    @Transactional(readOnly = true)
    public MemberSnsConnect findBySnsTypeAndSnsIdOrThrow(SnsType snsType,
                                                                 String snsId) {
        return findBySnsTypeAndSnsId(snsType, snsId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.BAD_REQUEST));
    }

    @Transactional(readOnly = true)
    public Optional<MemberSnsConnect> findBySnsTypeAndSnsId(SnsType snsType,
                                                                    String snsId) {
        return memberSnsConnectRepository.findBySnsTypeAndSnsId(snsType, snsId);
    }
}
