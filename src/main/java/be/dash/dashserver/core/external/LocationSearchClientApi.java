package be.dash.dashserver.core.external;

import be.dash.dashserver.core.domain.lesson.Locations;

public interface LocationSearchClientApi {
    Locations getLocations(String query);

}
