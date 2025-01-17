package be.dash.dashserver.api.core.advertisement;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.advertisement.dto.AdvertisementResponses;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import be.dash.dashserver.core.domain.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping
    public ResponseEntity<AdvertisementResponses> advertisement() {
        List<Advertisement> advertisement = advertisementService.getAdvertisement();
        return ResponseEntity.ok(AdvertisementResponses.from(advertisement));
    }
}
