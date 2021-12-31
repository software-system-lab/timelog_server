package ssl.ois.timelog.adapter.view.model.log.dash.board;

import java.util.*;


public class TeamDashBoardViewModel {
    private String totalTime;
    private Map<String, Data> dataMap; 
    private List<MemberDashboard> memberDashboardList;

    public TeamDashBoardViewModel() {
        this.dataMap = new HashMap<>();
        this.memberDashboardList = new ArrayList<>();
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public Map<String, Data> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Data> dataMap) {
        this.dataMap = dataMap;
    }

    public void add(String activityTypeName, Data data) {
        Data d = this.dataMap.get(activityTypeName);
        if (d == null) {
            this.dataMap.put(activityTypeName, data);
        } else {
            d.setTimeLength(d.getTimeLength() + data.getTimeLength());
            d.setHour(d.getHour() + data.getHour());
            d.setMinute(d.getMinute() + data.getMinute());
        }
    }

    public List<MemberDashboard> getMemberDashboardList(){
        return this.memberDashboardList;
    }

    public void addMember(MemberDashboard memberDashboard){
        this.memberDashboardList.add(memberDashboard);
    }

    public static class Data {
        private long startTime;
        private long endTime;
        private long timeLength;
        private int hour;
        private int minute;

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

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }

    public static class MemberDashboard {
        private String username;
        private String totalTime;
        private Map<String, Data> dataMap; 
    
        public MemberDashboard(String username) {
            this.dataMap = new HashMap<>();
            this.username = username;
        }
    
        public void setUsername(String username){
            this.username = username;
        }

        public String getUsername(){
            return this.username;
        }

        public String getTotalTime() {
            return totalTime;
        }
    
        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }
    
        public Map<String, Data> getDataMap() {
            return dataMap;
        }
    
        public void setDataMap(Map<String, Data> dataMap) {
            this.dataMap = dataMap;
        }
    
        public void add(String activityTypeName, Data data) {
            Data d = this.dataMap.get(activityTypeName);
            if (d == null) {
                this.dataMap.put(activityTypeName, data);
            } else {
                d.setTimeLength(d.getTimeLength() + data.getTimeLength());
                d.setHour(d.getHour() + data.getHour());
                d.setMinute(d.getMinute() + data.getMinute());
            }
        }

    }
}
