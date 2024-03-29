package core.repository.authority;

import core.domain.authority.MemberAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {

    List<MemberAuthority> findAllByMemberId(Long memberId);
}
