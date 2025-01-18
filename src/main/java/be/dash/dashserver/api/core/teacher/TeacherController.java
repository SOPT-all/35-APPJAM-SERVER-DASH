package be.dash.dashserver.api.core.teacher;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.teacher.dto.CreateTeacherRequest;
import be.dash.dashserver.api.core.teacher.dto.CreateTeacherResponse;
import be.dash.dashserver.api.core.teacher.dto.TeacherResponses;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;
import be.dash.dashserver.core.domain.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<TeacherResponses> search() {
        List<TeacherLessonGenres> searched = teacherService.search();
        return ResponseEntity.ok(TeacherResponses.from(searched));
    }

    @Permission(role = Role.MEMBER)
    @PostMapping
    public ResponseEntity<CreateTeacherResponse> create(@MemberId Long memberId,
                                                        @Valid @RequestBody CreateTeacherRequest request) {
        return ResponseEntity.ok(CreateTeacherResponse.from(teacherService.create(request.toCommand(memberId))));

    }

    @GetMapping("/popular")
    public ResponseEntity<TeacherResponses> popular() {
        List<TeacherLessonGenres> popular = teacherService.popular();
        return ResponseEntity.ok(TeacherResponses.from(popular));
    }
}
