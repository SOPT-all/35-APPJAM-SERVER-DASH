package be.dash.dashserver.database.core.teacher;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.database.core.common.BaseTimeEntity;
import be.dash.dashserver.database.core.member.MemberJpaEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "teacher")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherJpaEntity extends BaseTimeEntity {

    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberJpaEntity member;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = true)
    private String education;

    @Column(nullable = true)
    private String experience;

    @Column(nullable = true, unique = true)
    private String instagram;

    @Column(nullable = true, unique = true)
    private String youtube;

    @Builder
    public TeacherJpaEntity(MemberJpaEntity member, String detail, String education, String experience, String instagram, String youtube) {
        this.member = member;
        this.detail = detail;
        this.education = education;
        this.experience = experience;
        this.instagram = instagram;
        this.youtube = youtube;
    }

    public TeacherJpaEntity(Teacher teacher) {
        this.id = teacher.getId();
        this.member = new MemberJpaEntity(teacher.getMember());
        this.detail = teacher.getDetail();
        this.education = teacher.getEducation();
        this.experience = teacher.getExperience();
        this.instagram = teacher.getInstagram();
        this.youtube = teacher.getYoutube();
    }

    public Teacher toDomain() {
        return Teacher.builder()
                .id(id)
                .member(member.toDomain())
                .detail(detail)
                .education(education)
                .experience(experience)
                .instagram(instagram)
                .youtube(youtube)
                .build();
    }

    public Teacher toDomainWithTeacherImage(TeacherImageJpaEntity teacherImageJpaEntity) {
        return Teacher.builder()
                .id(id)
                .member(member.toDomain())
                .detail(detail)
                .education(education)
                .experience(experience)
                .instagram(instagram)
                .youtube(youtube)
                .imageUrl(teacherImageJpaEntity.getImageUrl())
                .build();
    }
}
