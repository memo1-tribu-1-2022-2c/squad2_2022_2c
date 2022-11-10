package fi.uba.ar.memo.project.controller;

import fi.uba.ar.memo.project.model.Project;
import fi.uba.ar.memo.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Project createProject(@RequestBody String description) { return this.projectService.createProject(description); }
}
