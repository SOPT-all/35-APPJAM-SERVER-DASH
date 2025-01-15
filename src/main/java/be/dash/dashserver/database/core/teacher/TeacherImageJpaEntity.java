package be.dash.dashserver.database.core.teacher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import be.dash.dashserver.database.core.common.BaseCreatedAtEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "teacher_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherImageJpaEntity extends BaseCreatedAtEntity {

    @Id
    @Column(name = "teacher_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeacherJpaEntity teacher;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public TeacherImageJpaEntity(TeacherJpaEntity teacher, String imageUrl) {
        this.teacher = teacher;
        this.imageUrl = imageUrl;
    }
}
