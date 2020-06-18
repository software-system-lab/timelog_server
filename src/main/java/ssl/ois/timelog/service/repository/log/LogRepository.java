package ssl.ois.timelog.service.repository.log;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;

import java.net.ConnectException;
import java.util.List;

public interface LogRepository {
    public void save(Log log) throws SaveLogErrorException;
    public Log findByID(String id) throws GetLogErrorException;
    public List<Log> getByUserID(String userID) throws GetLogErrorException;
    public Boolean removeByID(String logID) throws GetLogErrorException, SaveLogErrorException;
}
