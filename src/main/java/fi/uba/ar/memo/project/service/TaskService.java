package fi.uba.ar.memo.project.service;

import fi.uba.ar.memo.project.dtos.ResourceData;
import fi.uba.ar.memo.project.exceptions.BadFieldException;
import fi.uba.ar.memo.project.exceptions.ResourceNotFound;
import fi.uba.ar.memo.project.model.Resource;
import fi.uba.ar.memo.project.model.Task;
import fi.uba.ar.memo.project.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
            task.update(newTask);
            this.taskRepository.save(task);
        } else {
            throw new ResourceNotFound("Task was not found");
        }
    }

    private ResourceData getDataFromResourceId(Resource resource) {
        return null;
    }

    public List<ResourceData> getResourceDataList(Task task) {
        return task.getResources()
                .stream()
                .map(r -> this.getDataFromResourceId(r))
                .collect(Collectors.toList());
    }
}
