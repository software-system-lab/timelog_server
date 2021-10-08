package ssl.ois.timelog.service.log.team.list;

import ssl.ois.timelog.service.log.LogDTO;

import java.util.ArrayList;
import java.util.List;

public class ListTeamLogUseCaseOutput {
    private List<LogDTO> logDTOList;

    public ListTeamLogUseCaseOutput() {
        this.logDTOList = new ArrayList<>();
    }

    public List<LogDTO> getLogDTOList() {
        return logDTOList;
    }

    public void setLogDTOList(List<LogDTO> logDTOList) {
        this.logDTOList = logDTOList;
    }
}
