package be.dash.dashserver.core.domain.teacher;

import java.util.List;
import be.dash.dashserver.core.domain.lesson.Images;
import be.dash.dashserver.core.domain.lesson.Videos;
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
    private final Images images;
    private final Videos videos;
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
        this.images = new Images(imageUrls);
        this.videos = new Videos(videoUrls);
        this.lessonCount = lessonCount;
    }

    public String getRepresentativeImageUrl() {
        return images.getFirstImage();
    }

    public List<String> getImageUrls() {
        return images.getImageUrls();
    }

    public List<String> getVideoUrls() {
        return videos.getVideoUrls();
    }
}
