package be.dash.dashserver.database.core.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import be.dash.dashserver.core.auth.RefreshToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
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
