package be.dash.dashserver.core.domain.home.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import lombok.RequiredArgsConstructor;

import static be.dash.dashserver.core.domain.lesson.LessonSortOption.LATEST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final AdvertisementRepository advertisementRepository;
    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;

    public List<Advertisement> getAdvertisement() {
        return advertisementRepository.getAdvertisement();
    }

    public Lessons getRecommendationLessons(Long memberId) {
        if (isGuest(memberId)) {
            Lessons lessons = new Lessons(lessonRepository.findActiveLessons(LocalDateTime.now()));
            return lessons.sort(LATEST);
        }
        Member member = memberRepository.findById(memberId);
        // TODO: Member를 통해 Student의 장르와 난이도를 가져와서 아래 메서드에 넣어줘야함
        Lessons lessons = new Lessons(
                lessonRepository.findActiveLessonsByGenreOrLevel(LocalDateTime.now(), List.of(), List.of())
        );
        return lessons.sort(LATEST);
    }

    private boolean isGuest(Long memberId) {
        return Objects.isNull(memberId);
    }
}
