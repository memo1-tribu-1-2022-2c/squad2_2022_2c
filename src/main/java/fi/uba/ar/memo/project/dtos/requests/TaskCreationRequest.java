package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.Priority;
import fi.uba.ar.memo.project.dtos.State;
import fi.uba.ar.memo.project.model.Task;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCreationRequest {
    private Long projectId;

    private String name;

    private String description;

    private State state;

    private LocalDateTime startingDate;

    private LocalDateTime endingDate;

    private LocalDateTime realEndingDate;

    private Priority priority;

    private Double estimatedHours;

    private Long previousTaskId;

    public TaskCreationRequest(Task other) {
        this.name = other.getName();
        this.description = other.getDescription();
        this.state = other.getState();
        this.startingDate = other.getStartingDate();
        this.endingDate = other.getEndingDate();
        this.estimatedHours = other.getEstimatedHours();
        this.realEndingDate = other.getRealEndingDate();
        this.priority = other.getPriority();
        this.previousTaskId = other.getPreviousTaskId();
    }

}
