package be.dash.dashserver.core.domain.member;

import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Student {
    private final long id;
    private final String profileImageUrl;
    private final Level level;
    private final List<Genre> genres;
}
