package be.dash.dashserver.api.core.home;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.home.dto.AdvertisementResponses;
import be.dash.dashserver.api.core.lesson.dto.LessonResponses;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import be.dash.dashserver.core.domain.home.service.HomeService;
import be.dash.dashserver.core.domain.lesson.Lessons;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/advertisement")
    public ResponseEntity<AdvertisementResponses> advertisement() {
        List<Advertisement> advertisement = homeService.getAdvertisement();
        return ResponseEntity.ok(AdvertisementResponses.from(advertisement));
    }

    @GetMapping("/recommendations-lesson")
    public ResponseEntity<LessonResponses> lesson(@MemberId Long memberId) {
        Lessons lessons = homeService.getRecommendationLessons(memberId);
        return ResponseEntity.ok(new LessonResponses(lessons));
    }
}
