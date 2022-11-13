package fi.uba.ar.memo.project.exceptions;

public class TaskNotFinishedException extends RuntimeException{
    public TaskNotFinishedException(String msg) {
        super(msg);
    }
}
