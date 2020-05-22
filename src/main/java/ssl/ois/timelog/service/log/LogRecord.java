package ssl.ois.timelog.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssl.ois.timelog.model.log.Log;

import java.net.ConnectException;
import java.util.UUID;

@Service
public class LogRecord {
    private LogRepository logRepository;

    public LogRecord(@Autowired LogRepository logRepo) {
        this.logRepository = logRepo;
    }
    public void execute(RecordInput input, RecordOutput output) {
        UUID userID = UUID.fromString(input.getUserID());
        Log log = new Log(userID, input.getTitle(), input.getStartTime(), input.getEndTime(), input.getDescription());
        try {
            logRepository.save(log);
        } catch (ConnectException e) {
            return;
        }
        output.setLogID(log.getLogID().toString());
    }
}
