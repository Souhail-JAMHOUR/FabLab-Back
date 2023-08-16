package ma.odc.fablabback.repositories;

import ma.odc.fablabback.entities.Docs.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
