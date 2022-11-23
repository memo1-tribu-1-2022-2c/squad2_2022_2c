package fi.uba.ar.memo.project.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceData {

    @JsonProperty("legajo")
    private int legajo;

    @JsonProperty("Nombre")
    private String nombre;

    @JsonProperty("Apellido")
    private String apellido;

}
