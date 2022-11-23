package fi.uba.ar.memo.project.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @JsonProperty("id")
    private int id;

    @JsonProperty("razon social")
    private String razonSocial;

    @JsonProperty("CUIT")
    private String cuit;

}
