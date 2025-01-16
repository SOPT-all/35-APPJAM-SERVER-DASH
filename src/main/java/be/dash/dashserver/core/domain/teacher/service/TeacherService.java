package be.dash.dashserver.core.domain.teacher.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;
import be.dash.dashserver.core.domain.teacher.Teachers;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;

    public List<TeacherLessonGenres> search() {
        Teachers teachers = teacherRepository.findTeachersSortByLessonCountsDesc();
        List<TeacherLessonGenres> teacherLessonGenres = new ArrayList<>();
        teachers.teachers().forEach(teacher -> {
            List<Genre> genres = lessonRepository.findDistinctGenresByTeacherIdOrderByCountDesc(teacher.getId());
            teacherLessonGenres.add(new TeacherLessonGenres(teacher, genres));
        });
        return teacherLessonGenres;
    }
}
