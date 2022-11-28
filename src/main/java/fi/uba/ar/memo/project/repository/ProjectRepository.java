package fi.uba.ar.memo.project.repository;

import fi.uba.ar.memo.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByClientId(int id);
}
