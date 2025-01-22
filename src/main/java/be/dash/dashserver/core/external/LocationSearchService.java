package be.dash.dashserver.core.external;

import org.springframework.stereotype.Service;
import be.dash.dashserver.core.domain.lesson.Locations;
import be.dash.dashserver.core.log.annotation.Trace;
import lombok.RequiredArgsConstructor;

@Trace
@Service
@RequiredArgsConstructor
public class LocationSearchService {
    private final LocationSearchClientApi locationSearchClientApi;

    public Locations getLocations(String query) {
        return locationSearchClientApi.getLocations(query);
    }
}
