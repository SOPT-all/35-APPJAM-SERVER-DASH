package be.dash.dashserver.core.domain.teacher;

import be.dash.dashserver.core.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Teacher {

    private final Long id;
    private final Member member;
    private final String detail;
    private final String education;
    private final String experience;
    private final String instagram;
    private final String youtube;
    private final String imageUrl;
    private final long lessonCount;

    @Builder
    public Teacher(
            Long id,
            Member member,
            String detail,
            String education,
            String experience,
            String instagram,
            String youtube,
            String imageUrl,
            long lessonCount) {
        this.id = id;
        this.member = member;
        this.detail = detail;
        this.education = education;
        this.experience = experience;
        this.instagram = instagram;
        this.youtube = youtube;
        this.imageUrl = imageUrl;
        this.lessonCount = lessonCount;
    }
}
