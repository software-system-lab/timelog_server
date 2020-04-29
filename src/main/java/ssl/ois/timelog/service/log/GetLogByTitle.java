package ssl.ois.timelog.service.log;

import ssl.ois.timelog.model.log.Log;

import java.util.List;

public class GetLogByTitle {
    private LogRepository logRepository;

    public GetLogByTitle(LogRepository repo) {
        this.logRepository = repo;
    }

    public void execute(GetByTitleInput input, GetByTitleOutput output) {
        List<Log> logList = this.logRepository.getByName(input.getUserID(), input.getTitle());

    }
}
