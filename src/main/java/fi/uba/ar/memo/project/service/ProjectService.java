package fi.uba.ar.memo.project.service;

import fi.uba.ar.memo.project.dtos.requests.ProjectCreationRequest;
import fi.uba.ar.memo.project.exceptions.BadDateRangeException;
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

    public Project createProject(ProjectCreationRequest request) {
        if (!request.getStartingDate().isBefore(request.getEndingDate())) {
            throw new BadDateRangeException(String.format("Starting date %s must be before ending date %s.",
                    request.getStartingDate(), request.getEndingDate()));
        }
        Project project = new Project(request);
        projectRepository.save(project);
        return project;
    }

    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

}
