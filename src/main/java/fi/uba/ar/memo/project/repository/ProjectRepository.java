package fi.uba.ar.memo.project.repository;

import fi.uba.ar.memo.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
