package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.Client;
import fi.uba.ar.memo.project.model.Project;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse extends ProjectCreationRequest {

    private int id;

    private String razonSocial;

    private String cuit;

    private Double estimatedHours;

    public ProjectResponse(Project request, Optional<Client> client) {
        super(request);
        this.estimatedHours = 0d;
        if (request.getTasks() != null) {
            request.getTasks()
                    .stream()
                    .forEach(t -> estimatedHours += t.getEstimatedHours());
        }
        if (client.isPresent()) {
            this.id = client.get().getId();
            this.razonSocial = client.get().getRazonSocial();
            this.cuit = client.get().getCuit();
        }
    }

}
