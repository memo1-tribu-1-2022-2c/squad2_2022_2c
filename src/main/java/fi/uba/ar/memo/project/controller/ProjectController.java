package fi.uba.ar.memo.project.controller;

import fi.uba.ar.memo.project.dtos.Client;
import fi.uba.ar.memo.project.dtos.requests.ProjectCreationRequest;
import fi.uba.ar.memo.project.dtos.requests.ProjectResponse;
import fi.uba.ar.memo.project.dtos.requests.TaskCreationRequest;
import fi.uba.ar.memo.project.exceptions.*;
import fi.uba.ar.memo.project.model.Project;
import fi.uba.ar.memo.project.model.Task;
import fi.uba.ar.memo.project.service.ProjectService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
        try {
            Optional<Project> project = this.projectService.getProject(id);
            if (project.isEmpty()) throw new ResourceNotFound(String.format("El proyecto con id {} no se encontro", id));
            Optional<Client> client = this.projectService.getClientDataFromId(project.get().getClientId());
            return ResponseEntity.of(Optional.of(new ProjectResponse(project.get(), client)));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createProject(@RequestBody ProjectCreationRequest request) {
        try {
            Project createdProject = this.projectService.createProject(request);
            Optional<Client> client = this.projectService.getClientDataFromId(request.getClientId());
            return ResponseEntity.of(Optional.of(new ProjectResponse(createdProject, client)));
        } catch (BadDateRangeException e) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(e.getMessage());
        } catch (BadFieldException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/client/{id}")
    public ResponseEntity getProjectByClientId(@RequestParam int id) {
        try {
            Optional<Project> project = this.projectService.getProjectByClientId(id);
            if (project.isEmpty()) throw new ResourceNotFound(String.format("El proyecto con id {} no se encontro", id));
            Optional<Client> client = this.projectService.getClientDataFromId(project.get().getClientId());
            return ResponseEntity.of(Optional.of(new ProjectResponse(project.get(), client)));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectResponse> findListOfProjects() {
        return this.projectService.getAllProjects();
    }

    @PatchMapping(path = "/{id}/endproject")
    public ResponseEntity endProject(@PathVariable Long id) {
        try {
            this.projectService.endProject(id);
            return ResponseEntity.ok().build();
        } catch (TaskAlreadyFinishedExcepiton e) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getMessage());
        } catch (ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (TaskNotFinishedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/createtask", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createTask(@RequestBody TaskCreationRequest request) {
        try {
            Task createdTask = this.projectService.createTask(request);
            return ResponseEntity.of(Optional.of(createdTask));
        } catch (BadDateRangeException e) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(e.getMessage());
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadFieldException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity changeProject(@RequestBody Project project) {
        try {
            this.projectService.updateProject(project);
            return ResponseEntity.ok().build();
        } catch (BadDateRangeException e) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(e.getMessage());
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
