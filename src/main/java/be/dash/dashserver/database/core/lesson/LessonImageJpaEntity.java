package be.dash.dashserver.database.core.lesson;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import be.dash.dashserver.database.core.common.BaseCreatedAtEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "lesson_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonImageJpaEntity extends BaseCreatedAtEntity {

    @Id
    @Column(name = "lesson_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long lessonId;

    @Column(nullable = false)
    private String imageUrl;

    public LessonImageJpaEntity(Long lessonId, String imageUrl) {
        this.lessonId = lessonId;
        this.imageUrl = imageUrl;
    }
}
