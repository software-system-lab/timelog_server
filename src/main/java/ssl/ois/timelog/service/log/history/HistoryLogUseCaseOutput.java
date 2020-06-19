package ssl.ois.timelog.service.log.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryLogUseCaseOutput {
    private List<LogDTO> logDTOList;

    public HistoryLogUseCaseOutput() {
        this.logDTOList = new ArrayList<>();
    }

    public List<LogDTO> getLogDTOList() {
        return logDTOList;
    }

    public void setLogDTOList(List<LogDTO> logDTOList) {
        this.logDTOList = logDTOList;
    }

    public static class LogDTO {
        private String activityTypeName;
        private String title;
        private String startTime;
        private String endTime;

        public String getActivityTypeName() {
            return activityTypeName;
        }

        public void setActivityTypeName(String activityTypeName) {
            this.activityTypeName = activityTypeName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
