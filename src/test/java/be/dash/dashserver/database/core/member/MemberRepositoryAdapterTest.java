package be.dash.dashserver.database.core.member;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.member.service.StudentGenreRepository;
import be.dash.dashserver.database.core.student.StudentGenreJpaRepository;
import be.dash.dashserver.database.core.student.StudentJpaRepository;
import be.dash.dashserver.database.fixture.MemberJpaEntityFixture;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(MemberRepositoryAdapter.class)
class MemberRepositoryAdapterTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private StudentJpaRepository studentRepository;
    @Autowired
    private StudentGenreJpaRepository studentGenreRepository;

    @Test
    @DisplayName("온보딩시에 멤버, 학생, 장르 엔티티가 저장된다.")
    void onboard() {
        // given
        MemberJpaEntity member = MemberJpaEntityFixture.createWithoutOnboarding();
        memberJpaRepository.save(member);
        Member domainMember = Member.builder()
                .id(1L)
                .name("실명")
                .phoneNumber("01000000000")
                .nickname("닉네임")
                .student(Student.builder()
                        .level(Level.ADVANCED)
                        .genres(List.of(Genre.BRAKING, Genre.KPOP))
                        .profileImageUrl("image.com")
                        .build())
                .build();
        // when
        memberRepository.onboard(domainMember);
        // then
        Assertions.assertThat(memberJpaRepository.findById(1L).get().getName()).isEqualTo("실명");
        Assertions.assertThat(studentRepository.findById(1L).get().getLevel()).isEqualTo(Level.ADVANCED);
        Assertions.assertThat(studentRepository.findById(1L).get().getProfileImageUrl()).isEqualTo("image.com");
        Assertions.assertThat(studentGenreRepository.findAll().size()).isEqualTo(2);
    }
}
