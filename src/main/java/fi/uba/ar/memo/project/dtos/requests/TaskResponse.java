package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.ResourceData;
import fi.uba.ar.memo.project.model.Task;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse extends TaskCreationRequest {

    List<ResourceData> resources;

    private Long id;

    private Double workedHours;

    public TaskResponse(Task request, List<ResourceData> resources) {
        super(request);
        this.id = request.getId();
        this.workedHours = request.getWorkedHours();
        this.resources = resources;
    }
}
