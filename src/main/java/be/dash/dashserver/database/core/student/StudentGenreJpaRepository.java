package be.dash.dashserver.database.core.student;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentGenreJpaRepository extends JpaRepository<StudentGenreJpaEntity, Long> {

    @Query("select distinct sg from StudentGenreJpaEntity sg " +
            "join fetch sg.student st " +
            "where st.id = :studentId")
    List<StudentGenreJpaEntity> findAllStudentGenresWithStudent(@Param("studentId") Long studentId);
}
