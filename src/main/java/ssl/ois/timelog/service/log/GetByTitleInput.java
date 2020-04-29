package ssl.ois.timelog.service.log;

public class GetByTitleInput {
    private String userID;
    private String title;

    public void setUserID(String userID){
        this.userID = userID;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUserID(){
        return this.userID;
    }

    public String getTitle(){
        return this.title;
    }
}
