package be.dash.dashserver.database.core.token;

import be.dash.dashserver.core.auth.RefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Long memberId;

    public RefreshTokenJpaEntity(String refreshToken, Long memberId) {
        this.refreshToken = refreshToken;
        this.memberId = memberId;
    }

    static RefreshTokenJpaEntity from(String refreshToken, long memberId) {
        return new RefreshTokenJpaEntity(refreshToken, memberId);
    }

    RefreshToken toDomain() {
        return new RefreshToken(memberId, refreshToken);
    }
}
