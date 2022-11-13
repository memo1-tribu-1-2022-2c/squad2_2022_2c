
package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.ProjectType;
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

    private ProjectType projectType;
}
