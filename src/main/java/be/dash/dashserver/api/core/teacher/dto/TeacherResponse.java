package be.dash.dashserver.api.core.teacher.dto;

import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;

public record TeacherResponse(long id, String nickname, String profileImage, List<Genre> genres) {
    private static final int MAX_GENRE_COUNT = 2;

    public TeacherResponse(TeacherLessonGenres teacherLessonGenres) {
        this(
                teacherLessonGenres.teacher().getId(),
                teacherLessonGenres.teacher().getMember().getNickname(),
                teacherLessonGenres.teacher().getImages().getFirstImage(),
                selectGenres(teacherLessonGenres.genres())
        );
    }

    public static List<Genre> selectGenres(List<Genre> genres) {
        if (genres.size() >= MAX_GENRE_COUNT) {
            return List.of(genres.get(0), genres.get(1));
        }
        return genres;
    }
}
