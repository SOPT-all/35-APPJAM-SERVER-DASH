package be.dash.dashserver.core.domain.lesson;

import java.util.List;
import lombok.Getter;

@Getter
public class Videos {
    private List<String> videoUrls;

    public Videos(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public String getRepresentativeVideoUrl() {
        return videoUrls.get(0);
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }
}
