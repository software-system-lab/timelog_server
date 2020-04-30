package ssl.ois.timelog.service.log;

import ssl.ois.timelog.model.log.Log;

import java.util.List;
import java.util.UUID;

public class GetLogById {
    private LogRepository logRepository;

    public GetLogById(LogRepository repo) {
        this.logRepository = repo;
    }

    public void execute(GetByIdInput input, GetByIdOutput output) {
        Log log = this.logRepository.getByID(UUID.fromString(input.getLogID()));
        output.setTitle(log.getTitle());
        output.setStartTime(log.getStartTime());
        // List<Log> logList = this.logRepository.getByName(input.getUserID(), input.getTitle());

    }
}
