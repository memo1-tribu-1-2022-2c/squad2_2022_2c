package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.Client;
import fi.uba.ar.memo.project.dtos.State;
import fi.uba.ar.memo.project.model.Project;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse extends ProjectCreationRequest {

    private Long projectId;

    private String razonSocial;

    private String cuit;

    private Double estimatedHours;

    private State state;

    public ProjectResponse(Project request, Optional<Client> client) {
        super(request);
        this.projectId = request.getId();
        this.state = request.getState();
        this.estimatedHours = 0d;
        if (request.getTasks() != null) {
            request.getTasks()
                    .stream()
                    .forEach(t -> estimatedHours += t.getEstimatedHours());
        }
        if (client.isPresent()) {
            this.razonSocial = client.get().getRazonSocial();
            this.cuit = client.get().getCuit();
        }
    }

}
