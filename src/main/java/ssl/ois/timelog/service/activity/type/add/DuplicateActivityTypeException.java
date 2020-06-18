package ssl.ois.timelog.service.activity.type.add;

public class DuplicateActivityTypeException extends Exception {
    public DuplicateActivityTypeException() {
        super("Duplicate activity type");
    }
}