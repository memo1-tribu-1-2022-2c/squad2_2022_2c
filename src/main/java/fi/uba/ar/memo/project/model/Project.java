package fi.uba.ar.memo.project.model;

import fi.uba.ar.memo.project.dtos.ProjectType;
import fi.uba.ar.memo.project.dtos.State;
import fi.uba.ar.memo.project.dtos.requests.ProjectCreationRequest;
import fi.uba.ar.memo.project.exceptions.BadDateRangeException;
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

    private ProjectType projectType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private List<Task> tasks;

    public Project(ProjectCreationRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.state = State.NEW;
        this.startingDate = request.getStartingDate();
        this.endingDate = request.getEndingDate();
        this.projectType = request.getProjectType();

        this.assertTimeRanges(request.getStartingDate(), request.getEndingDate());

        if (name == null || name.isBlank() || projectType == null ||
            description == null || state == null ||
            startingDate == null || endingDate == null) {
            throw new BadFieldException("No fields should be blank or null to create a project.");
        }
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    private void assertTimeRanges(LocalDateTime startingDate, LocalDateTime endingDate) {
        if (!startingDate.isBefore(endingDate)) {
            throw new BadDateRangeException(String.format("Starting date %s must be before ending date %s.",
                    startingDate, endingDate));
        }
    }

    public void update(Project other) {
        if (other.getStartingDate() != null && other.getEndingDate() != null)
            this.assertTimeRanges(other.getStartingDate(), other.getEndingDate());

        if (other.getName() != null) {
            this.name = other.getName();
        }
        if (other.getDescription() != null) {
            this.description = other.getDescription();
        }
        if (other.getState() != null) {
            this.state = other.getState();
        }
        if (other.getStartingDate() != null) {
            this.startingDate = other.getStartingDate();
        }
        if (other.getEndingDate() != null) {
            this.endingDate = other.getEndingDate();
        }
        if (other.getProjectType() != null) {
            this.projectType = other.getProjectType();
        }
    }
}
