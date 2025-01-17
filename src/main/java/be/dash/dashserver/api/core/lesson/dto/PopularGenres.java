package be.dash.dashserver.api.core.lesson.dto;

import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;

public record PopularGenres(List<Genre> genres) {
    private static int MAX_GENRES_SIZE = 3;

    public PopularGenres {
        if (genres.size() >= MAX_GENRES_SIZE) {
            genres = genres.subList(0, MAX_GENRES_SIZE);
        }
    }
}
