package be.dash.dashserver.database.fixture;

import be.dash.dashserver.database.core.member.MemberJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherJpaEntity;

public class TeacherJpaEntityFixture {
    private TeacherJpaEntityFixture() {
    }

    public static TeacherJpaEntity create(MemberJpaEntity memberJpaEntity) {
        return TeacherJpaEntity.builder()
                .member(memberJpaEntity)
                .detail("경력 10년의 힙합 댄서")
                .education("한국예술대학교 댄스학과")
                .experience("다양한 공연 및 강의 경험")
                .instagram("@hong_dancer")
                .youtube("youtube.com/hong_dancer")
                .build();
    }

    public static TeacherJpaEntity createWithNickname(String nickname, MemberJpaEntity memberJpaEntity) {
        return TeacherJpaEntity.builder()
                .member(memberJpaEntity)
                .detail("경력 10년의 힙합 댄서")
                .education("한국예술대학교 댄스학과")
                .experience("다양한 공연 및 강의 경험")
                .instagram("@hong_dancer" + nickname)
                .youtube("youtube.com/hong_dancer" + nickname)
                .build();
    }
}
