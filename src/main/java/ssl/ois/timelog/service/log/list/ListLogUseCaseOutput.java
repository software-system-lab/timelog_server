package ssl.ois.timelog.service.log.list;

import ssl.ois.timelog.model.log.Log;

import java.util.ArrayList;
import java.util.List;

public class ListLogUseCaseOutput {
    private List<Log> logs;

    public ListLogUseCaseOutput() {
        this.logs = new ArrayList<>();
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}
