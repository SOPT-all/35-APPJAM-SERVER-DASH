package be.dash.dashserver.api.core.lesson;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.lesson.dto.CreateLessonRequest;
import be.dash.dashserver.api.core.lesson.dto.LessonDetailResponse;
import be.dash.dashserver.api.core.lesson.dto.LessonFilterRequest;
import be.dash.dashserver.api.core.lesson.dto.LessonReservationResponse;
import be.dash.dashserver.api.core.lesson.dto.LessonResponses;
import be.dash.dashserver.api.core.lesson.dto.PopularGenres;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Keyword;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.service.LessonService;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.service.MemberService;
import be.dash.dashserver.api.core.lesson.dto.PaymentRequest;
import be.dash.dashserver.core.domain.reservation.service.ReservationService;
import be.dash.dashserver.core.log.annotation.Trace;
import lombok.RequiredArgsConstructor;

@Trace
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final ReservationService reservationService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<LessonResponses> search(@ModelAttribute LessonFilterRequest lessonFilterRequest,
                                                  @RequestParam(required = false, defaultValue = "LATEST", name = "sortOption") LessonSortOption sortOption,
                                                  @RequestParam(required = false, defaultValue = Keyword.ANY, name = "keyword") Keyword keyword) {
        Lessons searched = lessonService.search(lessonFilterRequest.genre(),
                lessonFilterRequest.level(),
                lessonFilterRequest.startDate(),
                lessonFilterRequest.endDate(),
                keyword,
                sortOption);
        return ResponseEntity.ok(new LessonResponses(searched));
    }

    @Permission(role = Role.TEACHER)
    @PostMapping
    public ResponseEntity<Void> create(@MemberId Long memberId,
                                       @Valid @RequestBody CreateLessonRequest request) {
        lessonService.createLesson(request.toCommand(memberId));
        return ResponseEntity.ok().build();

    }

    @GetMapping("/recommendations")
    public ResponseEntity<LessonResponses> recommendation(@MemberId Long memberId, @RequestParam(required = false, defaultValue = "LATEST", name = "sortOption") LessonSortOption sortOption) {
        Lessons lessons = lessonService.getRecommendationLessons(memberId, sortOption);
        return ResponseEntity.ok(new LessonResponses(lessons));
    }

    @GetMapping("/popular-genres")
    public ResponseEntity<PopularGenres> popularGenres() {
        List<Genre> popularGenres = lessonService.getPopularGenres();
        return ResponseEntity.ok(new PopularGenres(popularGenres));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<LessonResponses> upcoming() {
        Lessons searched = lessonService.searchBySortOption(LessonSortOption.UPCOMING);
        return ResponseEntity.ok(new LessonResponses(searched));
    }

    @Permission(role = {Role.MEMBER, Role.TEACHER})
    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDetailResponse> findById(
            @MemberId Long memberId,
            @PathVariable @Min(value = 1L, message = "수업의 식별자는 양수로 이루어져야 합니다.") long lessonId) {
        Lesson lesson = lessonService.findById(lessonId);
        boolean booked = reservationService.isBooked(memberId, lessonId);
        return ResponseEntity.ok(new LessonDetailResponse(lesson, booked));
    }

    @GetMapping("/{lessonId}/reserve-progress")
    public ResponseEntity<LessonReservationResponse> reserveProgress(
            @MemberId Long memberId,
            @PathVariable @Min(value = 1L, message = "수업의 식별자는 양수로 이루어져야 합니다.") long lessonId) {
        Lesson lesson = lessonService.findById(lessonId);
        Member member = memberService.findById(memberId);
        return ResponseEntity.ok(new LessonReservationResponse(lesson, member));
    }

    @PostMapping("/{lessonId}/reservations")
    public ResponseEntity<Void> createReservation(
            @MemberId Long memberId,
            @Valid @RequestBody PaymentRequest paymentRequest,
            @PathVariable@Min(value = 1L, message = "수업의 식별자는 양수로 이루어져야 합니다.") long lessonId) {
        long reservationId = reservationService.reserve(paymentRequest.toCommand(memberId, lessonId));
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }
}
