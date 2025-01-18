package be.dash.dashserver.api.core.advertisement.dto;

import java.util.List;
import be.dash.dashserver.core.domain.advertisement.Advertisement;

public record AdvertisementResponses(List<AdvertisementResponse> advertisements) {

    public static AdvertisementResponses from(List<Advertisement> advertisements) {
        return new AdvertisementResponses(advertisements.stream().map(AdvertisementResponse::new).toList());
    }
}
