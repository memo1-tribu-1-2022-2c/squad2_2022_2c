package fi.uba.ar.memo.project.dtos.requests;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleToResourceIdRequest {
    private Map<String, Long> roleToResourceId = new HashMap<String, Long>();
}
