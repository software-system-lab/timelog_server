package ssl.ois.timelog.service.team.dashboard;

import ssl.ois.timelog.service.log.LogDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public abstract class TeamDashboardUseCaseOutputBound implements TeamDashboardUseCaseOutput {
    private List<LogDTO> teamLogDTOList;
    private Map<String, List<LogDTO>> memberDashboardMap;   // Map<username, List<LogDTO>>
    private Map<String, String> memberDisplayNameMap;       // Map<username, displayName>

    public TeamDashboardUseCaseOutputBound() {
        this.teamLogDTOList = new ArrayList<>();
        this.memberDashboardMap = new HashMap<>();
        this.memberDisplayNameMap = new HashMap<>();
    }

    public List<LogDTO> getTeamLogDTOList() {
        return teamLogDTOList;
    }

    public void setTeamLogDTOList(List<LogDTO> teamLogDTOList) {
        this.teamLogDTOList = teamLogDTOList;
    }

    public void addMemberLog(String username, String displayName, List<LogDTO> memberLogDTOList) {
        this.memberDashboardMap.put(username, memberLogDTOList);
        this.memberDisplayNameMap.put(username, displayName);
    }

    public Map<String, List<LogDTO>> getMemberDashboardMap(){
        return this.memberDashboardMap;
    }

    public Map<String, String> getMemberDisplayNameMap() {
        return memberDisplayNameMap;
    }
}
