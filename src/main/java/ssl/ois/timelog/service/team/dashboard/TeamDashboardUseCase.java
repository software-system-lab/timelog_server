package ssl.ois.timelog.service.team.dashboard;

import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.log.LogDTO;
import ssl.ois.timelog.service.repository.log.LogRepository;
import ssl.ois.timelog.service.team.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.util.Pair; 


public class TeamDashboardUseCase {
    private LogRepository logRepository;

    public TeamDashboardUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void execute(TeamDashboardUseCaseInput input, TeamDashboardUseCaseOutput output)
            throws ParseException, DatabaseErrorException {
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
        for (Log log: logList) {
            LogDTO logDTO = new LogDTO();
            logDTO.setId(log.getID().toString());
            logDTO.setActivityTypeName(log.getActivityTypeName());
            logDTO.setTitle(log.getTitle());
            logDTO.setStartTime(simpleDateFormat.format(log.getStartTime()));
            logDTO.setEndTime(simpleDateFormat.format(log.getEndTime()));
            teamLogDTOList.add(logDTO);
        }
        output.setTeamLogDTOList(teamLogDTOList);

        //member dashboard
        for(Person member: input.getMemberList()){
            List<LogDTO> logDTOList = new ArrayList<>();
            for(Log log: logList) {
                if(log.getUserID().equals(member.getUserID())){
                    LogDTO logDTO = new LogDTO();
                    logDTO.setId(log.getID().toString());
                    logDTO.setActivityTypeName(log.getActivityTypeName());
                    logDTO.setTitle(log.getTitle());
                    logDTO.setStartTime(simpleDateFormat.format(log.getStartTime()));
                    logDTO.setEndTime(simpleDateFormat.format(log.getEndTime()));
                    logDTOList.add(logDTO);
                }
            }
            output.addMemberLog(member.getUsername(), logDTOList);
        }
    }
}
