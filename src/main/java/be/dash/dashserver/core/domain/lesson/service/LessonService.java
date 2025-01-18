package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;

    public Lessons search(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, LessonSortOption sortOption) {
        Lessons lessons = new Lessons(
                lessonRepository.findActiveLessonsByFilters(genre, level, startDateTime, endDateTime, LocalDateTime.now())
        );
        return lessons.sort(sortOption);
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
}
