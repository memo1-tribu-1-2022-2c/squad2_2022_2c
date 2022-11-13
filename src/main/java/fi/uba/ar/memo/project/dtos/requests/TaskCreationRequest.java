package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.State;
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

    private Double estimatedHours;
}
