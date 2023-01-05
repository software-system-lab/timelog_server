package ssl.ois.timelog.service.team.dashboard;

import ssl.ois.timelog.common.MemberDTO;
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
import java.util.stream.Collectors;


public class TeamDashboardUseCase {
    private LogRepository logRepository;
    private AccountManager accountManager;
    private UnitRepository unitRepository;

    private final String SSL = "Software System Lab";
    private final String SUNBIRD = "Sunbird";

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

        // get member of team
        Map<UUID, MemberDTO> memberMap = accountManager.getTeamRoleRelation(input.getTeamID());
        List<LogDTO> teamLogDTOList = new ArrayList<>();

        for (Map.Entry<UUID, MemberDTO> memberEntry: memberMap.entrySet()) {
            MemberDTO memberDTO = memberEntry.getValue();

            // get belonging team of user
            Map<UUID, String> belongTeams = accountManager.getBelongingTeams(memberDTO.getUsername());

            // generate belonging team id set
            Set<String> belongTeamIdSet = belongTeams
              .keySet()
              .stream()
              .map(UUID::toString).collect(Collectors.toSet());
            List<Log> memberLogList = new ArrayList<>();

            if (input.getTeamName() != null && (input.getTeamName().equals(SSL) || input.getTeamName().equals(SUNBIRD))) {
                for (String teamId: belongTeamIdSet) {
                    memberLogList.addAll(this.logRepository.findByPeriodAndUserIDWithTeamID(
                      teamId,
                      memberDTO.getUserId(),
                      input.getStartDate(),
                      dateFormat.format(endDate),
                      input.getFilterList()
                    ));
                }

                // filter duplicate log using map
                // logRepository.findByPeriodAndUserIDWithTeamID() will return both
                // the logs that the user added on the target team and the personal logs,
                // therefore, if the user belongs to multiple team, the personal logs will
                // be fetched multiple times.
                Map<String, Log> memberLogMap = new HashMap<>(); // Map<logId, Log>
                for (Log memberLog: memberLogList) {
                    memberLogMap.putIfAbsent(memberLog.getID().toString(), memberLog);
                }
                memberLogList = new ArrayList<>(memberLogMap.values());
            } else {
                memberLogList.addAll(this.logRepository.findByPeriodAndUserIDWithTeamID(
                        input.getTeamID(),
                        memberDTO.getUserId(),
                        input.getStartDate(),
                        dateFormat.format(endDate),
                        input.getFilterList()
                ));
            }

            // activity ids of teams
            Set<UUID> teamActivityIds = new HashSet<>();
            for (UUID teamId : belongTeams.keySet()) {
                teamActivityIds.addAll(this.unitRepository.getActivityMapperIDListByUnitID(teamId.toString()));
            }

            List<LogDTO> logDTOList = new ArrayList<>();
            for (Log log: memberLogList) {
                // check if the log belongs to team
                if (!teamActivityIds.contains(log.getActivityUserMapperID()) && input.getPersonal()) {
                    // personal log
                    log.setActivityTypeName(log.getActivityTypeName() + " (Personal)");
                    logDTOList.add(this.buildLogDTO(log));
                } else if (teamActivityIds.contains(log.getActivityUserMapperID())) {
                    // team log
                    logDTOList.add(this.buildLogDTO(log));
                    teamLogDTOList.add(this.buildLogDTO(log));
                }
            }

            output.addMemberLog(memberDTO.getUsername(), memberDTO.getDisplayName(), logDTOList);
        }

        output.setTeamLogDTOList(teamLogDTOList);
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
