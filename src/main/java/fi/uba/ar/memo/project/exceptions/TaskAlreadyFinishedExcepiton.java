package fi.uba.ar.memo.project.exceptions;

public class TaskAlreadyFinishedExcepiton extends RuntimeException{
    public TaskAlreadyFinishedExcepiton(String msg) {
        super(msg);
    }
}
