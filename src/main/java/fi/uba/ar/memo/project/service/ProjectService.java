package fi.uba.ar.memo.project.service;

import fi.uba.ar.memo.project.model.Project;
import fi.uba.ar.memo.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String description) {
        Project project = Project.builder().description(description).build();
        projectRepository.save(project);
        return project;
    }

    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

}
