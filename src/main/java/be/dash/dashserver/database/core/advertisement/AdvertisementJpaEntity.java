package be.dash.dashserver.database.core.advertisement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "advertisement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvertisementJpaEntity {

    @Id
    @Column(name = "advertisement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    public Advertisement toDomain() {
        return new Advertisement(imageUrl, description);
    }

    public AdvertisementJpaEntity(Advertisement advertisement) {
        this.imageUrl = advertisement.imageUrl();
        this.description = advertisement.description();
    }
}
