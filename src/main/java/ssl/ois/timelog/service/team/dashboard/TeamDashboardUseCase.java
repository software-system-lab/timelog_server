package ssl.ois.timelog.service.team.dashboard;

import ssl.ois.timelog.common.MemberDTO;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.manager.AccountManager;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.service.team.Person;

import java.lang.reflect.Member;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


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

        Map<UUID, MemberDTO> memberMap = accountManager.getTeamRoleRelation(input.getTeamID());

        Set<String> teamIdSet = new HashSet<>();
        for (Map.Entry<UUID, MemberDTO> memberEntry: memberMap.entrySet()) {
            MemberDTO memberDTO = memberEntry.getValue();
            teamIdSet.addAll(
                    accountManager.getBelongingTeams(memberDTO.getUsername())
                        .keySet()
                        .stream()
                        .map(UUID::toString)
                        .collect(Collectors.toList())
            );
        }

        List<Log> logList = new ArrayList<>();
        if (input.getSsl() != null) {
            for (String teamId: teamIdSet) {
                logList.addAll(this.logRepository.findByPeriodAndTeam(
                        teamId,
                        input.getStartDate(),
                        dateFormat.format(endDate),
                        input.getFilterList()
                ));
            }
        } else {
            logList.addAll(this.logRepository.findByPeriodAndTeam(
                    input.getTeamID(),
                    input.getStartDate(),
                    dateFormat.format(endDate),
                    input.getFilterList()
            ));
        }

        //team dashboard
        List<LogDTO> teamLogDTOList = new ArrayList<>();
        for (Log log : logList) {
            teamLogDTOList.add(this.buildLogDTO(log));
        }
        output.setTeamLogDTOList(teamLogDTOList);


        for (Map.Entry<UUID, MemberDTO> memberEntry: memberMap.entrySet()) {
            MemberDTO memberDTO = memberEntry.getValue();

            Map<UUID, String> teamMapperIdMap = new HashMap<>();
            //TeamID/TeamName
            Map<UUID, String> belongTeams = accountManager.getBelongingTeams(memberDTO.getUsername());
            for (Map.Entry<UUID, String> entry : belongTeams.entrySet()) {
                for (UUID id : unitRepository.getActivityMapperIDListByUnitID(entry.getKey().toString())) {
                    teamMapperIdMap.put(id, entry.getValue());
                }
            }

            List<Log> memberLogList = new ArrayList<>();
            if (input.getSsl() != null) {
                for (Map.Entry<UUID, String> belongTeam: belongTeams.entrySet()) {
                    memberLogList.addAll(this.logRepository.findByPeriodAndUserIDWithTeamID(
                            belongTeam.getKey().toString(),
                            memberDTO.getUserId(),
                            input.getStartDate(),
                            dateFormat.format(endDate),
                            input.getFilterList()
                    ));
                }
            } else {
                memberLogList.addAll(this.logRepository.findByPeriodAndUserIDWithTeamID(
                        input.getTeamID(),
                        memberDTO.getUserId(),
                        input.getStartDate(),
                        dateFormat.format(endDate),
                        input.getFilterList()
                ));
            }
            List<LogDTO> logDTOList = new ArrayList<>();

            for (Log log : memberLogList) {
                if (!teamMapperIdMap.containsKey(log.getActivityUserMapperID()) && input.getPersonal()) {
                    log.setActivityTypeName(log.getActivityTypeName() + " (Personal)");
                    logDTOList.add(this.buildLogDTO(log));
                } else if (teamMapperIdMap.containsKey(log.getActivityUserMapperID())) {
                    logDTOList.add(this.buildLogDTO(log));
                }
            }

            output.addMemberLog(memberDTO.getUsername(), memberDTO.getDisplayName(), logDTOList);
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
