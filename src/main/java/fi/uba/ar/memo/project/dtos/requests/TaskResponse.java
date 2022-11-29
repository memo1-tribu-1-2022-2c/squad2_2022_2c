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

    public TaskResponse(Task request, List<ResourceData> resources) {
        super(request);
        this.resources = resources;
    }
}
