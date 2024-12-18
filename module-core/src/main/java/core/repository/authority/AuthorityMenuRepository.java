package core.repository.authority;

import core.common.enumeration.authority.AuthorityType;
import core.domain.authority.AuthorityMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface AuthorityMenuRepository extends JpaRepository<AuthorityMenu, Long> {

    List<AuthorityMenu> findAllByAuthorityIdAndMenuIdIn(Long authorityId,
                                                        Collection<Long> menuIds);

    List<AuthorityMenu> findAllByAuthority_TypeInAndAuthority_IsDisplay(Collection<AuthorityType> types,
                                                                        boolean isDisplay);

}
