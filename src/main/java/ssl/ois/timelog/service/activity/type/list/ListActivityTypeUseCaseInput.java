package ssl.ois.timelog.service.activity.type.list;

import java.util.List;
import java.util.UUID;

public class ListActivityTypeUseCaseInput {
//    private String userID;
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }
//
//    public String getUserID() {
//        return this.userID;
//    }

    private List<UUID> unitIdList;

    public void addUnitId(UUID unitId) {
        this.unitIdList.add(unitId);
    }

    public List<UUID> getUnitIdList(){
        return this.unitIdList;
    }
}