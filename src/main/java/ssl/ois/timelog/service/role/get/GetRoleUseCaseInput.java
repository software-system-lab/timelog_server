package ssl.ois.timelog.service.role.get;

import java.util.UUID;

public class GetRoleUseCaseInput {
    private UUID userID;
    private UUID teamID;

    public UUID getUserID(){
        return userID;
    }

    public void setUserID(UUID userID){
        this.userID = userID;
    }

    public UUID getTeamID(){
        return teamID;
    }

    public void setTeamID(UUID teamID){
        this.teamID = teamID;
    }
}