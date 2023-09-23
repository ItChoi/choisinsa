package core.service.authority;

import core.repository.authority.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

}
