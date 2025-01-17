package be.dash.dashserver.api.core.home.dto;

import be.dash.dashserver.core.domain.advertisement.Advertisement;

public record AdvertisementResponse(String imageUrl) {

    public AdvertisementResponse(Advertisement advertisement) {
        this(advertisement.imageUrl());
    }
}
