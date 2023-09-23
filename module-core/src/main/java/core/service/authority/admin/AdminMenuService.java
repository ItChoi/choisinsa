package core.service.authority.admin;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.repository.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class AdminMenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public boolean isMatchMenuCountWithInputAndOrigin(Collection<Long> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EMPTY);
        }

        return menuRepository.countByIdIn(menuIds) == menuIds.size();
    }
}
