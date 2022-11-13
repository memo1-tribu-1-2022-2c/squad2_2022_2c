package fi.uba.ar.memo.project.controller;

import fi.uba.ar.memo.project.exceptions.BadDateRangeException;
import fi.uba.ar.memo.project.exceptions.BadFieldException;
import fi.uba.ar.memo.project.exceptions.ResourceNotFound;
import fi.uba.ar.memo.project.model.Task;
import fi.uba.ar.memo.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(value = "/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PatchMapping(path = "/{id}/estimateHours/{hours}")
    public ResponseEntity estimateTaskHours(@PathVariable Long id, @PathVariable Double hours) {
        try {
            this.taskService.estimateHours(id, hours);
            return ResponseEntity.ok().build();
        } catch (BadFieldException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getTask(@PathVariable Long id)  {
        try {
            return ResponseEntity.of(this.taskService.getTask(id));
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateTask(@RequestBody Task task) {
        try {
            this.taskService.updateTask(task);
            return ResponseEntity.ok().build();
        } catch (BadDateRangeException e) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(e.getMessage());
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
