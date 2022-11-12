package fi.uba.ar.memo.project.model;

import fi.uba.ar.memo.project.dtos.State;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String name;

    private String description;

    private State state;

    private LocalDateTime startingDate;

    private LocalDateTime endingDate;

    private Double estimatedHours;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ticket_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )
    private List<Ticket> tickets;
}
