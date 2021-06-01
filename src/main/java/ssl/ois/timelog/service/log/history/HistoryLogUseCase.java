package ssl.ois.timelog.service.log.history;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HistoryLogUseCase {
    private LogRepository logRepository;
    private UnitRepository unitRepository;
    private AccountManager accountManager;

    public HistoryLogUseCase(LogRepository logRepository, UnitRepository unitRepository, AccountManager accountManager) {
        this.logRepository = logRepository;
        this.unitRepository = unitRepository;
        this.accountManager = accountManager;
    }

    public void execute(HistoryLogUseCaseInput input, HistoryLogUseCaseOutput output)
            throws ParseException, DatabaseErrorException, AccountErrorException {
        Date endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        endDate = dateFormat.parse(input.getEndDate());
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        List<Log> logList = this.logRepository.findByPeriod(input.getUserID(),
                input.getStartDate(), dateFormat.format(endDate));

        //MapperID/TeamName
        Map<UUID, String> teamMapperIdMap = new HashMap<>();

        //TeamID/TeamName
        Map<UUID, String> belongTeams = accountManager.getBelongingTeams(accountManager.getNameById(input.getUserID()));

        for(Map.Entry<UUID, String> entry: belongTeams.entrySet()){
            for(UUID id : unitRepository.getActivityMapperIDListByUnitID(entry.getKey().toString())){
                teamMapperIdMap.put(id, entry.getValue());
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Log.DATE_FORMAT);

        List<LogDTO> logDTOList = new ArrayList<>();
        if(input.getFilterList() == null){
            for (Log log: logList) {
                LogDTO logDTO = new LogDTO();
                logDTO.setId(log.getID().toString());
                logDTO.setActivityTypeName(log.getActivityTypeName());
                logDTO.setTitle(log.getTitle());
                logDTO.setStartTime(simpleDateFormat.format(log.getStartTime()));
                logDTO.setEndTime(simpleDateFormat.format(log.getEndTime()));
                if(teamMapperIdMap.containsKey(log.getActivityUserMapperID())){
                    logDTO.setTeamName(teamMapperIdMap.get(log.getActivityUserMapperID()));
                }
                logDTOList.add(logDTO);
            }
        } else {
            for (Log log: logList) {
                for(String filterName: input.getFilterList()) {
                    if(log.getActivityTypeName().equals(filterName)) {
                        LogDTO logDTO = new LogDTO();
                        logDTO.setId(log.getID().toString());
                        logDTO.setActivityTypeName(log.getActivityTypeName());
                        logDTO.setTitle(log.getTitle());
                        logDTO.setStartTime(simpleDateFormat.format(log.getStartTime()));
                        logDTO.setEndTime(simpleDateFormat.format(log.getEndTime()));
                        if(teamMapperIdMap.containsKey(log.getActivityUserMapperID())){
                            logDTO.setTeamName(teamMapperIdMap.get(log.getActivityUserMapperID()));
                        }
                        logDTOList.add(logDTO);
                    }
                }
            }
        }      

        output.setLogDTOList(logDTOList);
    }
}
