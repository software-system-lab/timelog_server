package ssl.ois.timelog.adapter.view.model.log.dash.board;

import java.util.ArrayList;
import java.util.List;

public class LogDashBoardViewModel {
    private String totalTime;
    private List<Data> data;

    public LogDashBoardViewModel() {
        this.data = new ArrayList<>();
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String activityTypeName;
        private long timeLength;
        private int hour;
        private int minute;

        public String getActivityTypeName() {
            return activityTypeName;
        }

        public void setActivityTypeName(String activityTypeName) {
            this.activityTypeName = activityTypeName;
        }

        public long getTimeLength() {
            return timeLength;
        }

        public void setTimeLength(long timeLength) {
            this.timeLength = timeLength;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }
    }
}
