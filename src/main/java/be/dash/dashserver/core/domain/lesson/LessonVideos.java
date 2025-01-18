package be.dash.dashserver.core.domain.lesson;

import java.util.List;
import lombok.Getter;

@Getter
public class LessonVideos {
    private List<String> videoUrls;

    public LessonVideos(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    String getRepresentativeVideoUrl() {
        return videoUrls.get(0);
    }
}
