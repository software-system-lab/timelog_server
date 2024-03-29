package ssl.ois.timelog.adapter.repository.memory;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MemoryLogRepository implements LogRepository {
    private Map<String, Log> logs;
    private UnitRepository unitRepository;

    public MemoryLogRepository() {
        this.logs = new HashMap<>();
    }

    public MemoryLogRepository(UnitRepository unitRepository) {
        this.logs = new HashMap<>();
        this.unitRepository = unitRepository;
    }

    @Override
    public void addLog(Log log) {
        this.logs.put(log.getID().toString(), log);
    }

    @Override
    public void updateLog(Log log, String targetID) {
        this.logs.replace(targetID, log);
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
    public UUID findActivityUserMapperID(String userID, String activityTypeName) {
        UUID activityUserMapperID = null;
        for (final Map.Entry<String, Log> logEntry: this.logs.entrySet()) {
            final Log log = logEntry.getValue();
            if (log.getUserID().toString().equals(userID) &&
                log.getActivityTypeName().equals(activityTypeName)) {
                activityUserMapperID = log.getActivityUserMapperID();
            }
        }
        return activityUserMapperID;
    }

    @Override
    public List<Log> findByPeriod(String userID, String startDateString, String endDateString) throws ParseException {
        final List<Log> logList = new ArrayList<>();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate;
        Date endDate;
        startDate = simpleDateFormat.parse(startDateString);
        endDate = simpleDateFormat.parse(endDateString);

        for (final Map.Entry<String, Log> logEntry: this.logs.entrySet()) {
            final Log log = logEntry.getValue();
            if (log.getUserID().toString().equals(userID) &&
                log.getStartTime().compareTo(startDate) >= 0 &&
                log.getEndTime().compareTo(endDate) < 0) {
                    logList.add(logEntry.getValue());
            }
        }
        return logList;
    }

    @Override
    public List<Log> findByPeriodAndTeam(String teamID, String startDateString, String endDateString, List<String> filter) throws DatabaseErrorException, ParseException {
        final List<Log> logList = new ArrayList<>();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate;
        Date endDate;
        startDate = simpleDateFormat.parse(startDateString);
        endDate = simpleDateFormat.parse(endDateString);

        List<String> activityIDs = this.unitRepository.getActivityMapperIDListByUnitID(teamID)
                .stream()
                .map(UUID::toString)
                .collect(Collectors.toList());

        for (final Map.Entry<String, Log> logEntry: this.logs.entrySet()) {
            final Log log = logEntry.getValue();
            if (activityIDs.contains(log.getActivityUserMapperID().toString()) &&
                log.getStartTime().compareTo(startDate) >= 0 &&
                log.getEndTime().compareTo(endDate) < 0) {
                    logList.add(logEntry.getValue());
            }
        }
        return logList;
    }

    @Override
    public List<Log> findByPeriodAndUserIDWithTeamID(String teamID, String userID, String startDateString, String endDateString, List<String> filter) throws ParseException {
        final List<Log> logList = new ArrayList<>();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate;
        Date endDate;
        startDate = simpleDateFormat.parse(startDateString);
        endDate = simpleDateFormat.parse(endDateString);

        for (final Map.Entry<String, Log> logEntry: this.logs.entrySet()) {
            final Log log = logEntry.getValue();
            if (log.getUserID().toString().equals(userID) &&
                log.getStartTime().compareTo(startDate) >= 0 &&
                log.getEndTime().compareTo(endDate) < 0) {
                    logList.add(logEntry.getValue());
            }
        }
        return logList;
    }
}
