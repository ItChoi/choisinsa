package core.repository.member;

import core.common.enumeration.SnsType;
import core.domain.member.MemberSnsConnect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSnsConnectRepository extends JpaRepository<MemberSnsConnect, Long> {

    boolean existsByMemberIdAndSnsType(Long memberId,
                                       SnsType snsType);

    Optional<MemberSnsConnect> findBySnsIdAndSnsType(String snsId,
                                                     SnsType snsType);

    boolean existsBySnsIdAndSnsType(String snsId,
                                    SnsType snsType);

    Optional<MemberSnsConnect> findByMemberIdAndSnsType(Long memberId,
                                                        SnsType snsType);

    Optional<MemberSnsConnect> findBySnsTypeAndSnsId(SnsType loginType,
                                                             String snsId);
}
