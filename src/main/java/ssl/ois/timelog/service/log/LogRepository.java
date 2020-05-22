package ssl.ois.timelog.service.log;

import ssl.ois.timelog.model.log.Log;

import java.net.ConnectException;
import java.util.UUID;

public interface LogRepository {
    public void save(Log log) throws ConnectException;
    public Log getByID(UUID id);
}
