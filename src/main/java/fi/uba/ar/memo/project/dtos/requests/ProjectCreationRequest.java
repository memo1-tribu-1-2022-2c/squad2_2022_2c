
package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.ProjectType;
import fi.uba.ar.memo.project.model.Project;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder
public class ProjectCreationRequest {

    private String name;

    private String description;

    private LocalDateTime startingDate;

    private LocalDateTime endingDate;

    private ProjectType projectType;

    private int clientId;

    private int versionId;

    public ProjectCreationRequest(Project other) {
        this.name = other.getName();
        this.description = other.getDescription();
        this.startingDate = other.getStartingDate();
        this.endingDate = other.getEndingDate();
        this.projectType = other.getProjectType();
        this.clientId = other.getClientId();
        if (other.getProjectType().equals(ProjectType.SOPORTE))
            this.versionId = other.getVersionId();
    }

}
