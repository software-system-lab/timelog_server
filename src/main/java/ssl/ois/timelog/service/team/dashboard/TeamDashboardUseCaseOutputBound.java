package ssl.ois.timelog.service.team.dashboard;

import ssl.ois.timelog.service.log.LogDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public abstract class TeamDashboardUseCaseOutputBound implements TeamDashboardUseCaseOutput {
    private List<LogDTO> teamLogDTOList;
    private Map<String, List<LogDTO>> memberDashboardMap;

    public TeamDashboardUseCaseOutputBound() {
        this.teamLogDTOList = new ArrayList<>();
        this.memberDashboardMap = new HashMap<>();
    }

    public List<LogDTO> getTeamLogDTOList() {
        return teamLogDTOList;
    }

    public void setTeamLogDTOList(List<LogDTO> teamLogDTOList) {
        this.teamLogDTOList = teamLogDTOList;
    }

    public void addMemberLog(String username, List<LogDTO> memberLogDTOList) {
        this.memberDashboardMap.put(username, memberLogDTOList);
    }

    public Map<String, List<LogDTO>> getMemberdashboardMap(){
        return this.memberDashboardMap;
    } 
}
