package core.service.authority;

import core.repository.authority.MemberCertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberCertificationService {

    private final MemberCertificationRepository memberCertificationRepository;


}
