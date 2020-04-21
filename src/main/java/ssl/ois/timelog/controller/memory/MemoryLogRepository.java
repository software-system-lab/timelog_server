package ssl.ois.timelog.controller.memory;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.LogRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryLogRepository implements LogRepository {
    private Map<UUID, Log> logs;

    public MemoryLogRepository() {
        this.logs = new HashMap<>();
    }

    public void save(Log log) {
        this.logs.put(log.getLogID(), log);
    }

    public Log getByID(UUID id) {
        return this.logs.get(id);
    }
}
