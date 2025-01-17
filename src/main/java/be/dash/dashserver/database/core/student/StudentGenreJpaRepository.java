package be.dash.dashserver.database.core.student;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentGenreJpaRepository extends JpaRepository<StudentGenreJpaEntity, Long> {

    @Query("select sg from StudentGenreJpaEntity sg " +
            "join fetch sg.student st " +
            "join fetch st.member m " +
            "where m.id = :memberId")
    Optional<StudentGenreJpaEntity> findByMemberId(@Param("memberId") Long memberId);
}
