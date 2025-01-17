package be.dash.dashserver.core.domain.home.service;

import java.util.List;
import be.dash.dashserver.core.domain.advertisement.Advertisement;

public interface AdvertisementRepository {
    List<Advertisement> getAdvertisement();
}
