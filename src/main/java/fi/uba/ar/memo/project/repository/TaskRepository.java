package fi.uba.ar.memo.project.repository;


import fi.uba.ar.memo.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
