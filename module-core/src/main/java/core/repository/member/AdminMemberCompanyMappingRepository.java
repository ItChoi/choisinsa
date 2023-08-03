package core.repository.member;

import core.domain.member.AdminMemberCompanyMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminMemberCompanyMappingRepository extends JpaRepository<AdminMemberCompanyMapping, Long> {

}