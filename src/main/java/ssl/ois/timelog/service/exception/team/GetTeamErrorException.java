package ssl.ois.timelog.service.exception.team;

public class GetTeamErrorException extends Exception{
    public GetTeamErrorException() {
        super("There are some error occurs when getting teams.");
    }
}
