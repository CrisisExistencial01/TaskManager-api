package TaskManager.TaskManager.exception;

public class DuplicateDataException extends RuntimeException{
    private String entity;
    private String field;
    private String value;

    public DuplicateDataException(String entity, String field, String value) {
        super(String.format("%s already exists with %s: %s", entity, field, value));
        this.entity = entity;
        this.field = field;
        this.value = value;
    }
}
