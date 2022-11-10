package fi.uba.ar.memo.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {

    @GetMapping(path = "/test/{name}")
    public String testEndpoint(@PathVariable String name) {
        return name;
    }

}
