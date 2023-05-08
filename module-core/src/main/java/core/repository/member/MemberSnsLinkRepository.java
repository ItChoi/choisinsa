package core.repository.member;

import com.mall.choisinsa.enumeration.SnsLoginType;
import core.domain.member.MemberSnsLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSnsLinkRepository extends JpaRepository<MemberSnsLink, Long> {

    boolean existsByMemberIdAndSnsType(Long memberId,
                                       SnsLoginType snsType);
}
