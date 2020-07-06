package ssl.ois.timelog.service.timebox.create;

public class CreateTimeboxUseCaseInput {
    
    private String title;
    private String startTime;
    private String endTime;

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
}