package fi.uba.ar.memo.project.controller;

import fi.uba.ar.memo.project.dtos.Priority;
import fi.uba.ar.memo.project.dtos.ResourceData;
import fi.uba.ar.memo.project.dtos.requests.TaskResponse;
import fi.uba.ar.memo.project.exceptions.BadDateRangeException;
import fi.uba.ar.memo.project.exceptions.BadFieldException;
import fi.uba.ar.memo.project.exceptions.ResourceNotFound;
import fi.uba.ar.memo.project.model.Resource;
import fi.uba.ar.memo.project.model.Task;
import fi.uba.ar.memo.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping(value = "/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PutMapping(path = "/{id}/resource/{resourceIdentifier}")
    public ResponseEntity addResource(@PathVariable Long id, @PathVariable int resourceIdentifier) {
        try {
            this.taskService.addResource(id, resourceIdentifier);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}/previous/{previousId}")
    public ResponseEntity setPreviousTask(@PathVariable Long id, @PathVariable Long previousId) {
        try {
            this.taskService.setPreviousTask(id, previousId);
            return ResponseEntity.ok().build();
        } catch (BadFieldException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

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

    @PatchMapping(path = "/{id}/priority/{priority}")
    public ResponseEntity setPriority(@PathVariable Long id, @PathVariable Priority priority) {
        try {
            this.taskService.setPriority(id, priority);
            return ResponseEntity.ok().build();
        } catch (BadFieldException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ResourceNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id)  {
        try {
            var task = this.taskService.getTask(id);
            List<ResourceData> resourceDataList = this.taskService.getResourceDataList(task.get());
            return ResponseEntity.of(Optional.of(new TaskResponse(task.get(), resourceDataList)));
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
