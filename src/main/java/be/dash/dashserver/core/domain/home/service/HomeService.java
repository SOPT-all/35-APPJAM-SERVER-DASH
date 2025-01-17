package be.dash.dashserver.core.domain.home.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final HomeRepository homeRepository;

    public List<Advertisement> getAdvertisement() {
        return homeRepository.getAdvertisement();
    }
}
