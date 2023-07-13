package core.repository.member;

import com.mall.choisinsa.enumeration.SnsType;
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
}
