package core.service.brand;

import core.domain.brand.BrandDetail;
import core.repository.brand.BrandDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class BrandDetailService {

    private final BrandDetailRepository brandDetailRepository;
}
