package ssl.ois.timelog.service.exception.team;

public class GetMemberOfErrorException extends Exception{
    public GetMemberOfErrorException() {
        super("There are some error occurs when getting teams belongs to.");
    }
}
