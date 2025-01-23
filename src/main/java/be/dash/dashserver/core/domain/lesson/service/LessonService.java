package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Keyword;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.command.CreateLessonCommand;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import be.dash.dashserver.core.exception.ForbiddenException;
import be.dash.dashserver.core.exception.NotFoundException;
import be.dash.dashserver.core.log.annotation.Trace;
import lombok.RequiredArgsConstructor;

@Trace
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final MemberRepository memberRepository;

    public Lessons search(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, Keyword keyword, LessonSortOption sortOption) {
        Lessons lessons = new Lessons(
                lessonRepository.findActiveLessonsByFilters(genre, level, startDateTime, endDateTime, keyword.getValue(), LocalDateTime.now())
        );
        return lessons.sort(sortOption);
    }

    @Transactional
    public void createLesson(CreateLessonCommand command) {
        Teacher teacher = teacherRepository.findByMemberId(command.memberId())
                .orElseThrow(() -> new NotFoundException("해당하는 선생님을 찾을 수 없습니다."));

        Lesson lesson = command.toDomainWith(teacher);
        lessonRepository.save(lesson);
    }

    public Lessons getRecommendationLessons(Long memberId, LessonSortOption lessonSortOption) {
        if (isGuest(memberId)) {
            Lessons lessons = new Lessons(lessonRepository.findActiveLessons(LocalDateTime.now()));
            return lessons.sort(lessonSortOption);
        }
        Student student = memberRepository.findStudentByMemberId(memberId);
        Lessons lessons = new Lessons(
                lessonRepository.findActiveLessonsByGenreOrLevel(LocalDateTime.now(), student.getGenres(), student.getLevel())
        );
        return lessons.sort(lessonSortOption);
    }

    private boolean isGuest(Long memberId) {
        return Objects.isNull(memberId);
    }

    public List<Genre> getPopularGenres() {
        return lessonRepository.popularGenres(LocalDateTime.now());
    }

    public Lessons searchBySortOption(LessonSortOption sortOption) {
        Lessons lessons = new Lessons(lessonRepository.findActiveLessons(LocalDateTime.now()));
        return lessons.sort(sortOption);
    }

    public Lesson findById(long lessonId) {
        return lessonRepository.findLessonsById(lessonId);
    }

    public List<Lesson> findAllByTeacherId(long teacherId) {
        return lessonRepository.findAllByTeacherIdOrderByStartDateTime(teacherId);
    }

    public void validateOwner(long teacherId, long lessonId) {
        if (!lessonRepository.existsByTeacherIdAndLessonId(teacherId, lessonId)) {
            throw new ForbiddenException("해당하는 수업에 대한 권한이 없습니다.");
        }
    }
}
