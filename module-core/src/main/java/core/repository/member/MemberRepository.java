package core.repository.member;

import core.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    boolean existsByLoginId(String loginId);
}
