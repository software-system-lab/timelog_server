package ssl.ois.timelog.service.log;

public class GetByIdOutput {
    private String logId;
    private String title;
    private String startTime;
    private String endTime;
    private String activityType;
    private String description;

    public void setLogId(String logId){
        this.logId = logId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setActivityType(String activityType){
        this.activityType = activityType;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getLogId(){
        return this.logId;
    }

    public String getTitle(){
        return this.title;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public String getActivityType(){
        return this.activityType;
    }

    public String getDescription(){
        return this.description;
    }
}
