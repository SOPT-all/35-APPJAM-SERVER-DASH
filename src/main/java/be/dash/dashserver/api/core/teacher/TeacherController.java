package be.dash.dashserver.api.core.teacher;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.core.teacher.dto.CreateTeacherRequest;
import be.dash.dashserver.api.core.teacher.dto.CreateTeacherResponse;
import be.dash.dashserver.api.core.teacher.dto.TeacherDetailResponse;
import be.dash.dashserver.api.core.teacher.dto.TeacherResponses;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.api.support.Permission;
import be.dash.dashserver.core.domain.common.Keyword;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;
import be.dash.dashserver.core.domain.teacher.service.TeacherService;
import be.dash.dashserver.core.domain.teacher.service.dto.TeacherDetailResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
@Validated
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<TeacherResponses> search(@RequestParam(required = false, defaultValue = Keyword.ANY) Keyword keyword) {
        List<TeacherLessonGenres> searched = teacherService.search(keyword);
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

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDetailResponse> find(@PathVariable @Min(value = 1L, message = "댄서의 식별자는 양수로 이루어져야 합니다.") long teacherId) {
        TeacherDetailResult teacherDetailResult = teacherService.find(teacherId);
        return ResponseEntity.ok(new TeacherDetailResponse(teacherDetailResult));
    }
}
