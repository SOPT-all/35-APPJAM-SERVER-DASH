package be.dash.dashserver.api.core.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.external.dto.LocationsResponse;
import be.dash.dashserver.core.domain.lesson.Locations;
import be.dash.dashserver.core.external.LocationSearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LocationSearchController {
    private final LocationSearchService locationSearchService;

    @GetMapping("/locations")
    public ResponseEntity<LocationsResponse> getLocations(@RequestParam final String query) {
        return ResponseEntity.ok(LocationsResponse.of(locationSearchService.getLocations(query)));
    }
}
