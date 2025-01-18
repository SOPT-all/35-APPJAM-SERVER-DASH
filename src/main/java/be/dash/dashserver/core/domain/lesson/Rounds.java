package be.dash.dashserver.core.domain.lesson;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class Rounds {
    private final List<Round> rounds;

    public Rounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public LocalDateTime getStartTime() {
        return rounds.get(0).getStartTime();
    }

    public LocalDateTime getEndTime() {
        return rounds.get(rounds.size()-1).getEndTime();
    }
}
