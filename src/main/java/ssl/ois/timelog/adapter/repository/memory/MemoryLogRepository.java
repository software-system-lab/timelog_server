package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.repository.log.LogRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Log> getByUserID(String userID) {
        List<Log> logs = new ArrayList<Log>();
        for(Log log: this.logs.values()) {
            if(log.getUserID().toString().equals(userID)) {
                logs.add(log);
            }
        }
        return Collections.unmodifiableList(logs);
    }
}
