package be.dash.dashserver.api.core.teacher.dto;

import java.util.List;
import be.dash.dashserver.core.domain.teacher.command.CreateTeacherCommand;

public record CreateTeacherRequest(
        String instagram,
        String youtube,
        List<String> educations,
        List<String> experiences,
        String detail,
        List<String> imageUrls,
        List<String> videoUrls
) {
    public CreateTeacherCommand toCommand(long memberId) {
        return new CreateTeacherCommand(memberId, instagram, youtube, educations, experiences, detail, imageUrls, videoUrls);
    }
}
