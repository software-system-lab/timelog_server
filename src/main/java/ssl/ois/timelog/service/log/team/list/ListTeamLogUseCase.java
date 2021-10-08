package ssl.ois.timelog.service.log.team.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ListTeamLogUseCase {
    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private AccountManager accountManager;

    public void execute(ListTeamLogUseCaseInput input, ListTeamLogUseCaseOutput output)
            throws ParseException, DatabaseErrorException, AccountErrorException {
        Date endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        endDate = dateFormat.parse(input.getEndDate());
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        List<Log> logList = this.logRepository.findByPeriod(input.getTeamId(),
                input.getStartDate(), dateFormat.format(endDate));

        //MapperID/TeamName
        Map<UUID, String> teamMapperIdMap = new HashMap<>();

        //TeamID/TeamName
        Map<UUID, String> belongTeams = accountManager.getBelongingTeams(accountManager.getNameById(input.getTeamId()));

        for(Map.Entry<UUID, String> entry: belongTeams.entrySet()){
            for(UUID id : unitRepository.getActivityMapperIDListByUnitID(entry.getKey().toString())){
                teamMapperIdMap.put(id, entry.getValue());
            }
        }
        System.out.println(teamMapperIdMap);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Log.DATE_FORMAT);

        List<LogDTO> logDTOList = new ArrayList<>();
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

        System.out.println(logDTOList);

        output.setLogDTOList(logDTOList);
    }
}
