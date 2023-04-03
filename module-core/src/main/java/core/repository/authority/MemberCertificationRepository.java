package core.repository.authority;

import core.domain.authority.MemberCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCertificationRepository extends JpaRepository<MemberCertification, Long> {

}
