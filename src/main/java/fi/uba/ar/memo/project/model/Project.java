package fi.uba.ar.memo.project.model;

import fi.uba.ar.memo.project.dtos.State;
import fi.uba.ar.memo.project.dtos.requests.ProjectCreationRequest;
import fi.uba.ar.memo.project.exceptions.BadFieldException;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project implements Serializable {

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private List<Task> tasks;

    public Project(ProjectCreationRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.state = State.NEW;
        this.startingDate = request.getStartingDate();
        this.endingDate = request.getEndingDate();

        if (name == null || name.isBlank() ||
            description == null || state == null ||
            startingDate == null || endingDate == null) {
            throw new BadFieldException("No fields should be blank or null to create a project.");
        }
    }
}
