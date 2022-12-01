package fi.uba.ar.memo.project.service;

import fi.uba.ar.memo.project.dtos.Priority;
import fi.uba.ar.memo.project.dtos.ResourceData;
import fi.uba.ar.memo.project.dtos.State;
import fi.uba.ar.memo.project.exceptions.BadFieldException;
import fi.uba.ar.memo.project.exceptions.ResourceNotFound;
import fi.uba.ar.memo.project.exceptions.TaskNotFinishedException;
import fi.uba.ar.memo.project.model.Resource;
import fi.uba.ar.memo.project.model.Task;
import fi.uba.ar.memo.project.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final RestTemplate restTemplate;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.restTemplate = new RestTemplate();

    }

    public void estimateHours(Long id, Double hours) {
        Optional<Task> taskFound = this.taskRepository.findById(id);
        if (taskFound.isPresent()) {
            if (hours < 0)  {
                throw new BadFieldException("Hours cannot be negative");
            }
            Task task = taskFound.get();
            task.setEstimatedHours(hours);
            taskRepository.save(task);
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    public void setPriority(Long id, Priority priority) {
        Optional<Task> taskFound = this.taskRepository.findById(id);
        if (taskFound.isPresent()) {
            Task task = taskFound.get();
            task.setPriority(priority);
            taskRepository.save(task);
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    public Optional<Task> getTask(Long id) {
        Optional<Task> taskFound = this.taskRepository.findById(id);
        if (taskFound.isPresent()) {
            return taskFound;
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    public void updateTask(Task newTask) {
        Optional<Task> taskFound = this.taskRepository.findById(newTask.getId());
        if (taskFound.isPresent()) {
            Task task = taskFound.get();
            if (newTask.getState().equals(State.FINALIZADO)) {
                // I will be checking the previous task already defined, not the new id
                this.validateThatICanFinish(task.getPreviousTaskId());
            }
            task.update(newTask);
            this.taskRepository.save(task);
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    private void validateThatICanFinish(Long previousTaskId) {
        Optional<Task> prevTask = this.taskRepository.findById(previousTaskId);
        if (prevTask.isPresent() && !prevTask.get().getState().equals(State.FINALIZADO)) {
            throw new TaskNotFinishedException(String.format("Cant finish this task without finishing the task with id {}", previousTaskId));
        }
    }

    private ResourceData getDataFromResourceId(Resource resource) {
        String url = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos";
        ResourceData[] resourcesResp = restTemplate.getForObject(url, ResourceData[].class);
        if (resourcesResp == null) {
            return new ResourceData();
        } else {
            List<ResourceData> resources = Arrays.asList(resourcesResp);
            return resources.stream()
                    .filter(r ->resource.getResourceIdentifier() == r.getLegajo())
                    .findFirst()
                    .orElse(null);
        }
    }

    public List<ResourceData> getResourceDataList(Task task) {
        return task.getResources()
                .stream()
                .map(r -> this.getDataFromResourceId(r))
                .collect(Collectors.toList());
    }

    public void setPreviousTask(Long id, Long previousId) {
        Optional<Task> optTask = this.taskRepository.findById(id);
        Optional<Task> prevTask = this.taskRepository.findById(previousId);
        if (optTask.isPresent() && prevTask.isPresent()) {
            Task task = optTask.get();
            if (!prevTask.get().getEndingDate().isBefore(task.getStartingDate())) {
                task.setPreviousTaskId(prevTask.get().getId());
                this.taskRepository.save(task);
            }
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    public void addResource(Long id, int resourceIdentifier) {
        Optional<Task> taskFound = this.taskRepository.findById(id);
        if (taskFound.isPresent()) {
            Task task = taskFound.get();
            task.getResources().add(Resource.builder().resourceIdentifier(resourceIdentifier).build());
            this.taskRepository.save(task);
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }
}
