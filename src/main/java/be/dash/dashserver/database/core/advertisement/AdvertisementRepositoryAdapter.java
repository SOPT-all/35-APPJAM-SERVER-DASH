package be.dash.dashserver.database.core.advertisement;

import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import be.dash.dashserver.core.domain.advertisement.service.AdvertisementRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdvertisementRepositoryAdapter implements AdvertisementRepository {

    private final AdvertisementJpaEntityRepository advertisementJpaEntityRepository;

    @Override
    public List<Advertisement> getAdvertisement() {
        return advertisementJpaEntityRepository.findAll().stream().map(AdvertisementJpaEntity::toDomain).toList();
    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        return advertisementJpaEntityRepository.save(new AdvertisementJpaEntity(advertisement)).toDomain();
    }
}
