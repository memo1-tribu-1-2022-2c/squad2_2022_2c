package fi.uba.ar.memo.project.dtos.requests;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectCreationRequest {
    private String name;

    private String description;

    private LocalDateTime startingDate;

    private LocalDateTime endingDate;
}
