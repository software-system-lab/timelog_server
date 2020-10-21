package ssl.ois.timelog.service.repository.log;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.log.GetLogErrorException;
import ssl.ois.timelog.service.exception.log.SaveLogErrorException;

import java.text.ParseException;
import java.util.List;

public interface LogRepository {
    void save(Log log) throws SaveLogErrorException;
    void update(Log log, String targetID) throws GetLogErrorException, SaveLogErrorException;
    Log findByID(String id) throws GetLogErrorException;
    Boolean removeByID(String logID) throws GetLogErrorException, SaveLogErrorException;
    List<Log> findByPeriod(String userID, String startDate, String endDate) throws ParseException, DatabaseErrorException;
    List<Log> findByPeriodandNotPrivate(String userID, String startDate, String endDate) throws ParseException, DatabaseErrorException;
}
