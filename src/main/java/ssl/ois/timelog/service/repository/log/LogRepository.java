package ssl.ois.timelog.service.repository.log;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;

import java.net.ConnectException;
import java.util.List;

public interface LogRepository {
    void save(Log log) throws SaveLogErrorException;
    Log findByID(String id) throws GetLogErrorException;
    List<Log> getByUserID(String userID) throws GetLogErrorException;
    Boolean removeByID(String logID) throws GetLogErrorException, SaveLogErrorException;
    List<Log> findByPeriod(String userID, String startDate, String endDate);
}
