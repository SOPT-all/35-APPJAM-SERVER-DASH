package be.dash.dashserver.core.domain.lesson;

import java.util.List;
import lombok.Getter;

@Getter
public class LessonImages {
    private List<String> imageUrls;

    public LessonImages(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    String getFirstImage() {
        return imageUrls.get(0);
    }
}
