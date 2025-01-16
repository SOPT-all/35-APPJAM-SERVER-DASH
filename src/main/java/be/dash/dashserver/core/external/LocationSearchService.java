package be.dash.dashserver.core.external;

import org.springframework.stereotype.Service;
import be.dash.dashserver.api.core.external.dto.LocationsResponse;
import be.dash.dashserver.core.domain.lesson.Locations;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationSearchService {
    private final LocationSearchClientApi locationSearchClientApi;
    public Locations getLocations(String query) {
        return locationSearchClientApi.getLocations(query);
    }
}
