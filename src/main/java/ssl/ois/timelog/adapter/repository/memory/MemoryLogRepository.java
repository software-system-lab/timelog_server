package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.repository.log.LogRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MemoryLogRepository implements LogRepository {
    private Map<String, Log> logs;

    public MemoryLogRepository() {
        this.logs = new HashMap<>();
    }

    @Override
    public void save(Log log) {
        this.logs.put(log.getID().toString(), log);
    }

    @Override
    public Log findByID(String id) {
        return this.logs.get(id);
    }

    @Override
    public Boolean removeByID(String id) {
        return this.logs.remove(id) != null;
    }

    @Override
    public List<Log> findByPeriod(String userID, String startDateString, String endDateString) {
        List<Log> logList = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate;
        Date endDate;
        try {
            startDate = simpleDateFormat.parse(startDateString);
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }

        for (Map.Entry<String, Log> logEntry: this.logs.entrySet()) {
            Log log = logEntry.getValue();
            if (log.getUserID().toString().equals(userID)) {
                if (log.getStartTime().compareTo(startDate) >= 0 && log.getEndTime().compareTo(endDate) < 0) {
                    logList.add(logEntry.getValue());
                }
            }
        }
        return logList;
    }

    @Override
    public List<Log> getByUserID(String userID) {
        List<Log> foundLogs = new ArrayList<>();
        for(Log log: this.logs.values()) {
            if(log.getUserID().toString().equals(userID)) {
                foundLogs.add(log);
            }
        }
        return Collections.unmodifiableList(foundLogs);
    }
}
