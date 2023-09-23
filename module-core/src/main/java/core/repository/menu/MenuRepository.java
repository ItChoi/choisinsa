package core.repository.menu;


import core.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    long countByIdIn(Collection<Long> menuIds);
}
