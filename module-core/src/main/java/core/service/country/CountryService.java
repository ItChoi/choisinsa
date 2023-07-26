package core.service.country;

import core.repository.country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;
}
