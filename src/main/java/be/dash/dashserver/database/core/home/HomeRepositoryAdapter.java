package be.dash.dashserver.database.core.home;

import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import be.dash.dashserver.core.domain.home.service.HomeRepository;
import be.dash.dashserver.database.core.advertisement.AdvertisementJpaEntity;
import be.dash.dashserver.database.core.advertisement.AdvertisementJpaEntityRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HomeRepositoryAdapter implements HomeRepository {

    private final AdvertisementJpaEntityRepository advertisementJpaEntityRepository;

    @Override
    public List<Advertisement> getAdvertisement() {
        return advertisementJpaEntityRepository.findAll().stream().map(AdvertisementJpaEntity::toDomain).toList();
    }
}
