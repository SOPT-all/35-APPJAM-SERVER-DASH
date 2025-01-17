package be.dash.dashserver.core.domain.teacher.command;

import java.util.List;

public record CreateTeacherCommand(long memberId,
                                   String instagram,
                                   String youtube,
                                   List<String> educations,
                                   List<String> experiences,
                                   String detail,
                                   List<String> imageUrls,
                                   List<String> videoUrls) {
}
