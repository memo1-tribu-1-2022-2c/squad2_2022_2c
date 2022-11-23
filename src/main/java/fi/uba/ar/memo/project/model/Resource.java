package fi.uba.ar.memo.project.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_resource")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int resourceIdentifier;
}
