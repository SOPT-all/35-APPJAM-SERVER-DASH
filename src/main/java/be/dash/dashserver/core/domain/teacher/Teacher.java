package be.dash.dashserver.core.domain.teacher;

import java.util.List;
import be.dash.dashserver.core.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Teacher {

    private final Long id;
    private final Member member;
    private final String detail;
    private final List<String> educations;
    private final List<String> experiences;
    private final String instagram;
    private final String youtube;
    private final List<String> imageUrls;
    private final List<String> videoUrls;
    private final long lessonCount;

    @Builder
    public Teacher(
            Long id,
            Member member,
            String detail,
            List<String> educations,
            List<String> experiences,
            String instagram,
            String youtube,
            List<String> imageUrls,
            List<String> videoUrls,
            long lessonCount) {
        this.id = id;
        this.member = member;
        this.detail = detail;
        this.educations = educations;
        this.experiences = experiences;
        this.instagram = instagram;
        this.youtube = youtube;
        this.imageUrls = imageUrls;
        this.videoUrls = videoUrls;
        this.lessonCount = lessonCount;
    }
}
