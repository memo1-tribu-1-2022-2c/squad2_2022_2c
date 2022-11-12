package fi.uba.ar.memo.project.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_risk")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Risk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String description;

    private Double probability;

    private String impact;
}
