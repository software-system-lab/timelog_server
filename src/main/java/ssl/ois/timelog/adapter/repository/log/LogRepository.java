package ssl.ois.timelog.adapter.repository.log;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.exception.DatabaseErrorException;
import ssl.ois.timelog.exception.log.GetLogErrorException;
import ssl.ois.timelog.exception.log.SaveLogErrorException;

import java.text.ParseException;
import java.util.List;

public interface LogRepository {
    void addLog(Log log) throws SaveLogErrorException;
    void updateLog(Log log) throws GetLogErrorException, SaveLogErrorException;
    Log findById(String id) throws GetLogErrorException, ParseException;
    List<Log> findByPeriod(String unitId, String startDate, String endDate) throws ParseException, DatabaseErrorException;
}
