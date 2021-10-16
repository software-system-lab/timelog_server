package ssl.ois.timelog.exception.log;

public class GetLogErrorException extends Exception{
    public GetLogErrorException(String logID) {
        super("Error occurred during getting log " + logID);
    }
}