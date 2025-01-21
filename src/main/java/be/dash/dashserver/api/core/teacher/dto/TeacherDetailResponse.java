package be.dash.dashserver.api.core.teacher.dto;

import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.teacher.service.dto.TeacherDetailResult;

public record TeacherDetailResponse(
        String instagram,
        String youtube,
        String nickname,
        String profileImage,
        List<Genre> genres,
        List<String> educations,
        List<String> experiences,
        String detail,
        List<String> imageUrls,
        List<String> videoUrls,
        List<LessonSummary> lessons
) {
    public TeacherDetailResponse(TeacherDetailResult teacherDetailResult) {
        this(teacherDetailResult.teacherLessonGenres().teacher().getInstagram(),
                teacherDetailResult.teacherLessonGenres().teacher().getYoutube(),
                teacherDetailResult.nickname(),
                teacherDetailResult.teacherLessonGenres().teacher().getRepresentativeImageUrl(),
                teacherDetailResult.teacherLessonGenres().genres(),
                teacherDetailResult.teacherLessonGenres().teacher().getEducations(),
                teacherDetailResult.teacherLessonGenres().teacher().getExperiences(),
                teacherDetailResult.teacherLessonGenres().teacher().getDetail(),
                teacherDetailResult.teacherLessonGenres().teacher().getImages().getImageUrls(),
                teacherDetailResult.teacherLessonGenres().teacher().getVideos().getVideoUrls(),
                teacherDetailResult.lessons().lessons().stream().map(LessonSummary::new).toList());
    }
}
