package ma.odc.fablabback.repositories.Docs;

import java.util.List;
import ma.odc.fablabback.entities.Docs.Project;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.enums.EProjectState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByMember(Member member);

  List<Project> findAllByOrderBySubmissionDateDesc();

  List<Project> findAllByProjectStateOrderBySubmissionDateDesc(EProjectState state);

  @Query("select p from Project p where p.title like :kw")
  List<Project> searchProject(@Param("kw") String keyword);
}
