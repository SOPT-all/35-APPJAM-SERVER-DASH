package be.dash.dashserver.database.fixture;

import be.dash.dashserver.database.core.teacher.TeacherImageJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherJpaEntity;

public class TeacherImageJpaEntityFixture {
    private TeacherImageJpaEntityFixture() {
    }

    public static TeacherImageJpaEntity create(TeacherJpaEntity teacherJpaEntity, String imageUrl) {
        return TeacherImageJpaEntity.builder()
                .teacher(teacherJpaEntity)
                .imageUrl(imageUrl).build();
    }
}
