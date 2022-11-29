package fi.uba.ar.memo.project.service;

import fi.uba.ar.memo.project.dtos.Client;
import fi.uba.ar.memo.project.dtos.State;
import fi.uba.ar.memo.project.dtos.requests.ProjectCreationRequest;
import fi.uba.ar.memo.project.dtos.requests.ProjectResponse;
import fi.uba.ar.memo.project.dtos.requests.RoleToResourceIdRequest;
import fi.uba.ar.memo.project.dtos.requests.TaskCreationRequest;
import fi.uba.ar.memo.project.exceptions.ResourceNotFound;
import fi.uba.ar.memo.project.exceptions.TaskAlreadyFinishedExcepiton;
import fi.uba.ar.memo.project.exceptions.TaskNotFinishedException;
import fi.uba.ar.memo.project.model.Project;
import fi.uba.ar.memo.project.model.Task;
import fi.uba.ar.memo.project.repository.ProjectRepository;
import fi.uba.ar.memo.project.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    private final RestTemplate restTemplate;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.restTemplate = new RestTemplate();
    }

    public Project createProject(ProjectCreationRequest request) {
        Project project = new Project(request);
        projectRepository.save(project);
        return project;
    }

    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getProjectByClientId(int id) {
        return projectRepository.findByClientId(id);
    }

    public void endProject(Long id) {
        Optional<Project> projectFound = this.projectRepository.findById(id);
        if (projectFound.isPresent()) {
            Project project = projectFound.get();
            if (project.getState().equals(State.FINALIZADO)) {
                throw new TaskAlreadyFinishedExcepiton("Project is already finished.");
            } else if (project.getTasks()
                              .stream()
                              .anyMatch(t -> !t.getState().equals(State.FINALIZADO))) {
                throw new TaskNotFinishedException("There are unfinished tasks.");
            } else {
                project.setState(State.FINALIZADO);
                this.projectRepository.save(project);
            }
        } else {
            throw new ResourceNotFound("The project was not found");
        }
    }

    public Task createTask(TaskCreationRequest request) {
        Optional<Project> projectFound = this.projectRepository.findById(request.getProjectId());
        if (projectFound.isPresent()) {
            Project project = projectFound.get();
            Task task = new Task(request);
            project.addTask(task);
            var createdProject = projectRepository.save(project);
            return Collections.max(createdProject.getTasks(), Comparator.comparing(Task::getId));
        } else {
            throw new ResourceNotFound("The project was not found");
        }
    }

    public void updateProject(Project newProject) {
        Optional<Project> projectFound = this.projectRepository.findById(newProject.getId());
        if (projectFound.isPresent()) {
            Project project = projectFound.get();
            project.update(newProject);
            this.projectRepository.save(project);
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    public List<ProjectResponse> getAllProjects() {
        return this.projectRepository.findAll()
                .stream()
                .map(p -> new ProjectResponse(p, getClientDataFromId(p.getClientId())))
                .collect(Collectors.toList());
    }

    public Optional<Client> getClientDataFromId(int clientId) {
        String url = "https://modulo-soporte.onrender.com/client/search?query=" + clientId;
        Client client = restTemplate.getForObject(url, Client.class);
        if (client == null) {
            return Optional.empty();
        } else {
            return Optional.of(client);
        }
    }

    public void addRoles(Long id, RoleToResourceIdRequest roles) {
        Optional<Project> projectFound = this.projectRepository.findById(id);
        if (projectFound.isPresent()) {
            Project project = projectFound.get();
            var dict = project.getRoleToResourceId();
            dict.putAll(roles.getRoleToResourceId());
            this.projectRepository.save(project);
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    public Map<String, Long> getRolesFromProject(Long id) {
        Optional<Project> projectFound = this.projectRepository.findById(id);
        if (projectFound.isPresent()) {
            Project project = projectFound.get();
            return project.getRoleToResourceId();
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }
}
