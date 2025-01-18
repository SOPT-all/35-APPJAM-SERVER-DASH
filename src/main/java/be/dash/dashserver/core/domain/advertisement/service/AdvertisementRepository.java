package be.dash.dashserver.core.domain.advertisement.service;

import java.util.List;
import be.dash.dashserver.core.domain.advertisement.Advertisement;

public interface AdvertisementRepository {
    List<Advertisement> getAdvertisement();

    Advertisement save(Advertisement advertisement);
}
