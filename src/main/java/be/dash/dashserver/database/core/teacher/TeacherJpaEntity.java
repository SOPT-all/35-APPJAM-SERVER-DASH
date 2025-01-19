package be.dash.dashserver.database.core.teacher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
        this.education = String.join(",", teacher.getEducations());
        this.experience = String.join(",", teacher.getExperiences());
        this.instagram = teacher.getInstagram();
        this.youtube = teacher.getYoutube();
    }

    public static TeacherJpaEntity fromDomain(Teacher teacher) {
        return TeacherJpaEntity.builder()
                .member(new MemberJpaEntity(teacher.getMember()))
                .detail(teacher.getDetail())
                .education(teacher.getEducations().stream().collect(Collectors.joining(",")))
                .experience(teacher.getExperiences().stream().collect(Collectors.joining(",")))
                .instagram(teacher.getInstagram())
                .youtube(teacher.getYoutube())
                .build();
    }

    public Teacher toDomain() {
        return Teacher.builder()
                .id(id)
                .member(member.toDomain())
                .detail(detail)
                .educations(Arrays.stream(education.split(",")).toList())
                .experiences(Arrays.stream(experience.split(",")).toList())
                .instagram(instagram)
                .youtube(youtube)
                .build();
    }

    public Teacher toDomainWithTeacherImage(List<TeacherImageJpaEntity> teacherImageJpaEntities) {
        return Teacher.builder()
                .id(id)
                .member(member.toDomain())
                .detail(detail)
                .educations(Arrays.stream(education.split(",")).toList())
                .experiences(Arrays.stream(experience.split(",")).toList())
                .instagram(instagram)
                .youtube(youtube)
                .imageUrls(teacherImageJpaEntities.stream().map(TeacherImageJpaEntity::getImageUrl).toList())
                .build();
    }

    public Teacher toDomainWithImageAndVideo(List<TeacherImageJpaEntity> teacherImageJpaEntities, List<TeacherVideoJpaEntity> teacherVideoJpaEntities) {
        return Teacher.builder()
                .id(id)
                .member(member.toDomain())
                .detail(detail)
                .educations(Arrays.stream(education.split(",")).toList())
                .experiences(Arrays.stream(experience.split(",")).toList())
                .instagram(instagram)
                .youtube(youtube)
                .imageUrls(teacherImageJpaEntities.stream().map(TeacherImageJpaEntity::getImageUrl).toList())
                .videoUrls(teacherVideoJpaEntities.stream().map(TeacherVideoJpaEntity::getVideoUrl).toList())
                .build();
    }
}
