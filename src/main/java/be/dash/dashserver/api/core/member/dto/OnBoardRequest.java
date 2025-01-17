package be.dash.dashserver.api.core.member.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;

public record OnBoardRequest(
        @NotBlank @Size(min = 2, max = 8) String name,
        @Size(min = 11, max = 11) @NotBlank String phoneNumber,
        @NotBlank @Size(min = 1, max = 8) String nickname,
        @NotNull Level level,
        @Size(min = 1, max = 3) List<Genre> genres,
        @NotBlank String profileImageUrl
) {
}
