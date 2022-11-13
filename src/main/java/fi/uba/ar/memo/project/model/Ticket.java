package fi.uba.ar.memo.project.model;

import fi.uba.ar.memo.project.dtos.Priority;
import fi.uba.ar.memo.project.dtos.State;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String title;

    private String description;

    private State state;

    private LocalDateTime projectName;

    private LocalDateTime projectVersion;

    private Priority priority;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tickets")
    private List<Task> tasks;
}
