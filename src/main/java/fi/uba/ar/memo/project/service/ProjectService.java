package fi.uba.ar.memo.project.service;

import fi.uba.ar.memo.project.dtos.State;
import fi.uba.ar.memo.project.dtos.requests.ProjectCreationRequest;
import fi.uba.ar.memo.project.exceptions.BadDateRangeException;
import fi.uba.ar.memo.project.exceptions.ProjectNotFound;
import fi.uba.ar.memo.project.exceptions.TaskAlreadyFinishedExcepiton;
import fi.uba.ar.memo.project.exceptions.TaskNotFinishedException;
import fi.uba.ar.memo.project.model.Project;
import fi.uba.ar.memo.project.model.Task;
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

    public void endProject(Long id) {
        Optional<Project> projectFound = this.projectRepository.findById(id);
        if (projectFound.isPresent()) {
            Project project = projectFound.get();
            if (project.getState().equals(State.FINISHED)) {
                throw new TaskAlreadyFinishedExcepiton("Project is already finished.");
            } else if (project.getTasks()
                              .stream()
                              .anyMatch(t -> !t.getState().equals(State.FINISHED))) {
                throw new TaskNotFinishedException("There are unfinished tasks.");
            } else {
                project.setState(State.FINISHED);
                this.projectRepository.save(project);
            }
        } else {
            throw new ProjectNotFound("The project was not found");
        }
    }
}
