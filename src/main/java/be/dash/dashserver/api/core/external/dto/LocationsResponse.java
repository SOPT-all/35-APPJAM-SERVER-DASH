package be.dash.dashserver.api.core.external.dto;

import java.util.List;
import be.dash.dashserver.core.domain.lesson.Locations;

public record LocationsResponse(
        List<LocationResponse> locations
) {
    public static LocationsResponse of(Locations locations) {
        return new LocationsResponse(
                locations.items().stream()
                        .map(LocationResponse::from)
                        .toList()
        );
    }
}
