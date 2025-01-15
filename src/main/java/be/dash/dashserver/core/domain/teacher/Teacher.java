package be.dash.dashserver.core.domain.teacher;

import be.dash.dashserver.core.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Teacher {

    private Long id;
    private Member member;
    private String detail;
    private String education;
    private String experience;
    private String instagram;
    private String youtube;
    private String imageUrl;

    @Builder
    public Teacher(
            Long id,
            Member member,
            String detail,
            String education,
            String experience,
            String instagram,
            String youtube,
            String imageUrl) {
        this.id = id;
        this.member = member;
        this.detail = detail;
        this.education = education;
        this.experience = experience;
        this.instagram = instagram;
        this.youtube = youtube;
        this.imageUrl = imageUrl;
    }
}
