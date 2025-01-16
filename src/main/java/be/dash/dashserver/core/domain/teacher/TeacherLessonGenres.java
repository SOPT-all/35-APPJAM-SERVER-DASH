package be.dash.dashserver.core.domain.teacher;

import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;

public record TeacherLessonGenres(Teacher teacher, List<Genre> genres) {
}
