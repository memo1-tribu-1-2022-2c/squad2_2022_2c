package fi.uba.ar.memo.project.controller;

import fi.uba.ar.memo.project.dtos.requests.ProjectCreationRequest;
import fi.uba.ar.memo.project.exceptions.BadDateRangeException;
import fi.uba.ar.memo.project.model.Project;
import fi.uba.ar.memo.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping(path = "/{id}")
    public Optional<Project> getProject(@PathVariable Long id) {
        return this.projectService.getProject(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createProject(@RequestBody ProjectCreationRequest description) {
        try {
            Project createdProject = this.projectService.createProject(description);
            return ResponseEntity.of(Optional.of(createdProject));
        } catch (BadDateRangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
