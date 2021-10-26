package ssl.ois.timelog.exception.activity;

public class RemoveActivityTypeException extends Exception {
    public RemoveActivityTypeException(String id) {
        super("Error occurred during remove " + id);
    }
}
