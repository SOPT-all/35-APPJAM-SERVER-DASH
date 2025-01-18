package be.dash.dashserver.api.core.lesson.dto;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.command.CreateLessonCommand;

public record createLessonRequest(
        @Size(min = 1) @NotNull List<@NotBlank String> imageUrls,
        @Size(max = 30) String name,
        @Size(max = 300) @NotBlank String detail,
        @Size(max = 5) List<@NotBlank String> videoUrls,
        long maxReservationCount,
        Genre genre,
        Level level,
        @Size(max = 200) String recommendation,
        int price,
        String location,
        String streetAddress,
        String oldStreetAddress,
        String detailedAddress,
        @Size(min = 1) List<RoundRequest> times
) {

    public CreateLessonCommand toCommand(Long memberId) {
        return new CreateLessonCommand(
                imageUrls,
                name,
                detail,
                videoUrls,
                maxReservationCount,
                genre,
                level,
                recommendation,
                price,
                location,
                streetAddress,
                oldStreetAddress,
                detailedAddress,
                times,
                memberId
        );
    }
}
