package ssl.ois.timelog.service.exception.role;

public class GetRoleErrorException extends Exception{
    public GetRoleErrorException() {
        super("There are some error occurs when getting role.");
    }
}
