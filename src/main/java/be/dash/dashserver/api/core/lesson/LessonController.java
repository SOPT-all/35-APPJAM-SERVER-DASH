package be.dash.dashserver.api.core.lesson;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.lesson.dto.LessonFilterRequest;
import be.dash.dashserver.api.core.lesson.dto.LessonResponses;
import be.dash.dashserver.api.core.lesson.dto.createLessonRequest;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.service.LessonService;
import be.dash.dashserver.core.domain.member.Role;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<LessonResponses> search(@ModelAttribute LessonFilterRequest lessonFilterRequest,
                                                  @RequestParam LessonSortOption sortOption) {
        Lessons searched = lessonService.search(lessonFilterRequest.genre(),
                lessonFilterRequest.level(),
                lessonFilterRequest.startDate(),
                lessonFilterRequest.endDate(),
                sortOption);
        return ResponseEntity.ok(new LessonResponses(searched));
    }

    @Permission(role = Role.TEACHER)
    @PostMapping
    public ResponseEntity<Void> create(@MemberId Long memberId,
                                       @Valid @RequestBody createLessonRequest request) {
        lessonService.createLesson(request.toCommand(memberId));
        return ResponseEntity.ok().build();

    }
}
