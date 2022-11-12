package fi.uba.ar.memo.project.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_iteration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Iteration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String objective;

    private LocalDateTime startingDate;

    private LocalDateTime endingDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "iteration_id")
    private List<Task> tasks;
}
