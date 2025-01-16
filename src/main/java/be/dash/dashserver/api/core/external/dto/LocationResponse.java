package be.dash.dashserver.api.core.external.dto;

import be.dash.dashserver.core.domain.lesson.Location;

public record LocationResponse(
        String name,
        String streetAddress
) {
    public static LocationResponse from(Location location) {
        return new LocationResponse(
                location.getTitle(),
                location.getRoadAddress()
        );
    }
}
