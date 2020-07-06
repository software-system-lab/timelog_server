package ssl.ois.timelog.model.timebox;

import java.util.UUID;

public class Timebox {

    private String title;
    private String startTime;
    private String endTime;
    private UUID id;

    public Timebox(String title, String startTime, String endTime){
        this.id = UUID.randomUUID();
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

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

    public UUID getID(){
        return id;
    }
}