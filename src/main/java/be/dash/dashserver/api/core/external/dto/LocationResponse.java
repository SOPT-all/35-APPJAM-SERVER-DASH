package be.dash.dashserver.api.core.external.dto;

import be.dash.dashserver.core.domain.lesson.Location;

public record LocationResponse(
        String location,
        String streetAddress,
        String oldStreetAddress) {
    public static LocationResponse from(Location location) {
        return new LocationResponse(
                trim(location.getTitle()),
                location.getRoadAddress(),
                location.getAddress()
        );
    }
    private static String trim(String string) {
        return string.replaceAll("<[^>]*>", "");
    }
}
