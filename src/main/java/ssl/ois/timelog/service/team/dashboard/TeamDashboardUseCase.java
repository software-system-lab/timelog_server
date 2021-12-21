package ssl.ois.timelog.service.team.dashboard;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.service.team.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class TeamDashboardUseCase {
    private LogRepository logRepository;
    private AccountManager accountManager;
    private UnitRepository unitRepository;

    public TeamDashboardUseCase(LogRepository logRepository, AccountManager accountManager, UnitRepository unitRepository) {
        this.logRepository = logRepository;
        this.accountManager = accountManager;
        this.unitRepository = unitRepository;
    }

    public void execute(TeamDashboardUseCaseInput input, TeamDashboardUseCaseOutput output)
            throws ParseException, DatabaseErrorException, AccountErrorException {
        Date endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        endDate = dateFormat.parse(input.getEndDate());
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();
        List<Log> logList = this.logRepository.findByPeriodAndTeam(input.getTeamID(),
                input.getStartDate(), dateFormat.format(endDate));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Log.DATE_FORMAT);

        //team dashboard
        List<LogDTO> teamLogDTOList = new ArrayList<>();
        if (input.getFilterList() == null) {
            for (Log log : logList) {
                teamLogDTOList.add(this.buildLogDTO(log));
            }
        } else {
            for (Log log : logList) {
                for (String filterName : input.getFilterList()) {
                    if (log.getActivityTypeName().equals(filterName)) {
                        teamLogDTOList.add(this.buildLogDTO(log));
                    }
                }
            }
        }
        output.setTeamLogDTOList(teamLogDTOList);

        //member dashboard
        for (Person member : input.getMemberList()) {
            Map<UUID, String> teamMapperIdMap = new HashMap<>();
            //TeamID/TeamName
            Map<UUID, String> belongTeams = accountManager.getBelongingTeams(member.getUsername());
            for (Map.Entry<UUID, String> entry : belongTeams.entrySet()) {
                for (UUID id : unitRepository.getActivityMapperIDListByUnitID(entry.getKey().toString())) {
                    teamMapperIdMap.put(id, entry.getValue());
                }
            }

            List<Log> memberLogList = this.logRepository.findByPeriodAndUserIDWithTeamID(input.getTeamID(),
                    member.getUserID().toString(), input.getStartDate(), dateFormat.format(endDate));
            List<LogDTO> logDTOList = new ArrayList<>();

            if(input.getFilterList() != null){
                for (Log log : memberLogList) {
                    // filter team log
                    for(String filterName: input.getFilterList()) {
                        if (log.getActivityTypeName().equals(filterName) && teamMapperIdMap.containsKey(log.getActivityUserMapperID())) {
                            logDTOList.add(this.buildLogDTO(log));
                        }
                    }
                    // filter personal log
                    if (input.getPersonal() && !teamMapperIdMap.containsKey(log.getActivityUserMapperID())) {
                        logDTOList.add(this.buildLogDTO(log));
                    }
                }
            } else {
                for (Log log : memberLogList) {
                    logDTOList.add(this.buildLogDTO(log));
                }
            }

            output.addMemberLog(member.getUsername(), logDTOList);
        }
    }

    private LogDTO buildLogDTO(Log log) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Log.DATE_FORMAT);

        LogDTO logDTO = new LogDTO();
        logDTO.setId(log.getID().toString());
        logDTO.setActivityTypeName(log.getActivityTypeName());
        logDTO.setTitle(log.getTitle());
        logDTO.setStartTime(simpleDateFormat.format(log.getStartTime()));
        logDTO.setEndTime(simpleDateFormat.format(log.getEndTime()));

        return logDTO;
    }
}
