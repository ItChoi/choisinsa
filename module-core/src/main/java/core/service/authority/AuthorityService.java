package core.service.authority;

import com.mall.choisinsa.enumeration.authority.AuthorityType;
import core.domain.authority.Authority;
import core.domain.authority.AuthorityMenu;
import com.mall.choisinsa.security.dto.menu.AuthorityApplicationReadyDto;
import core.repository.authority.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

}
