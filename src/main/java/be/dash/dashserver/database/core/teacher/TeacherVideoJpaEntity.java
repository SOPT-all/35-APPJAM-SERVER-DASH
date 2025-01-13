package be.dash.dashserver.database.core.teacher;

import be.dash.dashserver.database.core.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teacher_video")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherVideoJpaEntity extends BaseTimeEntity {

    @Id
    @Column(name = "teacher_video_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherJpaEntity teacher;

    @Column(nullable = false)
    private String videoUrl;

}
