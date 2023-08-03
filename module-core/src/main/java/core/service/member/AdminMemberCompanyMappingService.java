package core.service.member;

import core.domain.member.AdminMemberCompanyMapping;
import core.repository.member.AdminMemberCompanyMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class AdminMemberCompanyMappingService {

    private final AdminMemberCompanyMappingRepository adminMemberCompanyMappingRepository;

}
