package be.dash.dashserver.api.core.teacher.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import be.dash.dashserver.core.domain.teacher.command.CreateTeacherCommand;

public record CreateTeacherRequest(
        String instagram,
        String youtube,
        List<@Size(max = 30) String> educations,
        List<@Size(max = 30) String> experiences,
        @Size(max = 300) @NotBlank String detail,
        List<String> imageUrls,
        @Size(min = 1) List<String> videoUrls
) {
    public CreateTeacherCommand toCommand(long memberId) {
        return new CreateTeacherCommand(memberId, instagram, youtube, educations, experiences, detail, imageUrls, videoUrls);
    }
}
