package ssl.ois.timelog.service.exception.team;

public class DuplicateMemberException extends Exception{
    public DuplicateMemberException() {
        super("There are same member in the group.");
    }
}
