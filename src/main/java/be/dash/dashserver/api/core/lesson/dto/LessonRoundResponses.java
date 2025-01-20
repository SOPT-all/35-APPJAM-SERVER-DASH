package be.dash.dashserver.api.core.lesson.dto;

import java.util.List;
import be.dash.dashserver.core.domain.lesson.Rounds;

public record LessonRoundResponses(List<LessonRoundResponse> lessonRounds) {

    public static LessonRoundResponses from(Rounds rounds) {
        return new LessonRoundResponses(rounds.getRounds().stream()
                .map(round -> new LessonRoundResponse(round.getStartTime(), round.getEndTime()))
                .toList());
    }
}
