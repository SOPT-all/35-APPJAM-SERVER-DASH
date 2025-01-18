package be.dash.dashserver.core.domain.lesson;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Round {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Round(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
