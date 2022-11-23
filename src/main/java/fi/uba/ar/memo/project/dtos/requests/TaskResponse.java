package fi.uba.ar.memo.project.dtos.requests;

import fi.uba.ar.memo.project.dtos.ResourceData;
import fi.uba.ar.memo.project.model.Task;

import java.util.List;
import java.util.Optional;

public class TaskResponse extends TaskCreationRequest {

    List<ResourceData> resources;

    public TaskResponse(Task request, List<ResourceData> resources) {
        super(request);
        this.resources = resources;
    }
}
