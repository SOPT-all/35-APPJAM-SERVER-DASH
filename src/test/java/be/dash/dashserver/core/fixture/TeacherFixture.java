package be.dash.dashserver.core.fixture;

import java.util.List;
import be.dash.dashserver.core.domain.teacher.Teacher;

public class TeacherFixture {
    private TeacherFixture() {
    }

    public static Teacher create(long id, long memberId) {
        return Teacher.builder()
                .id(id)
                .member(MemberFixture.createTeacher(memberId))
                .detail("경력 10년의 힙합 댄서")
                .educations(List.of("한국예술대학교 댄스학과"))
                .experiences(List.of("다양한 공연 및 강의 경험"))
                .instagram("@hong_dancer")
                .youtube("youtube.com/hong_dancer")
                .imageUrls(List.of("www.example.com/teacher1.png"))
                .build();
    }

    public static Teacher createWithoutId(long memberId) {
        return Teacher.builder()
                .member(MemberFixture.createTeacher(memberId))
                .detail("경력 10년의 힙합 댄서")
                .educations(List.of("한국예술대학교 댄스학과"))
                .experiences(List.of("다양한 공연 및 강의 경험"))
                .instagram("@hong_dancer" + memberId)
                .youtube("youtube.com/hong_dancer" + memberId)
                .imageUrls(List.of("www.example.com/teacher1.png"))
                .build();
    }
}
