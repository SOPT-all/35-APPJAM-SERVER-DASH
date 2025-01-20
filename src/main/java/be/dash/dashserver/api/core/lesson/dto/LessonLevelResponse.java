package be.dash.dashserver.api.core.lesson.dto;

import java.util.Arrays;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.common.Level;

public enum LessonLevelResponse {
    BEGINNER(Level.BEGINNER, "입문", "춤에 처음 도전해보는 초보 댄서예요"),
    NOVICE(Level.NOVICE, "초급", "장르의 기본기를 본격적으로 배우고 싶어요"),
    INTERMEDIATE(Level.INTERMEDIATE, "중급", "루틴과 함께 배우며 장르의 이해도를 높이고 싶어요"),
    ADVANCED(Level.ADVANCED, "고급", "한계에 도전하며 나의 무브를 성장시키고 싶어요");

    private final Level level;
    private final String korLevel;
    private final String korDetail;

    LessonLevelResponse(Level level, String korLevel, String krDetail) {
        this.level = level;
        this.korLevel = korLevel;
        this.korDetail = krDetail;
    }

    public static LessonLevelResponse from(Level level) {
        return Arrays.stream(values()).filter(value -> value.level == level).findFirst()
                .orElseThrow(() -> new DashApiException("일치하는 난이도가 없습니다."));
    }

    public Level getLevel() {
        return level;
    }

    public String getKorLevel() {
        return korLevel;
    }

    public String getKorDetail() {
        return korDetail;
    }
}
