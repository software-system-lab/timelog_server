package ssl.ois.timelog.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.model.user.Activity;
import ssl.ois.timelog.model.user.User;
import ssl.ois.timelog.service.user.UserRepository;

@Service
public class LogRecord {
    private UserRepository userRepository;
    private LogRepository logRepository;

    public LogRecord(@Autowired UserRepository userRepo,
                     @Autowired LogRepository logRepo) {
        this.userRepository = userRepo;
        this.logRepository = logRepo;
    }
    public void execute(RecordInput input, RecordOutput output) {
        Log log = new Log(input.getUserID(), input.getTitle(), input.getStartTime(), input.getEndTime(), input.getDescription());
        logRepository.save(log);
        output.setLogID(log.getLogID().toString());
    }
}
