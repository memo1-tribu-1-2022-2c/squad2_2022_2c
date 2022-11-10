package fi.uba.ar.memo.project.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @BatchSize(size = 256)
    private String description;

}
