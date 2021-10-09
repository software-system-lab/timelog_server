package ssl.ois.timelog.service.exception.team;

public class MemberNotInGroupException extends Exception{
    public MemberNotInGroupException() {
        super("User is not in the group");
    }
}
