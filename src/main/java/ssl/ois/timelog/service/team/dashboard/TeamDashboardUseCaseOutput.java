package ssl.ois.timelog.service.team.dashboard;

import ssl.ois.timelog.service.log.LogDTO;

import java.util.List;
import java.util.Map;

public interface TeamDashboardUseCaseOutput {
    List<LogDTO> getTeamLogDTOList();
    void setTeamLogDTOList(List<LogDTO> teamLogDTOList);
    void addMemberLog(String username, String displayName, List<LogDTO> memberLogDTOList);
    Map<String, List<LogDTO>> getMemberDashboardMap();
    Map<String, String> getMemberDisplayNameMap();
}
