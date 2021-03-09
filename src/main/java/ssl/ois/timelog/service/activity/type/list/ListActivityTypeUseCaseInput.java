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

    private List<String> unitIdList;

    public void addUnitId(String unitId) {
        this.unitIdList.add(unitId);
    }

    public void setUnitIdList(List<String> unitIdList) {
        this.unitIdList = unitIdList;
    }

    public List<String> getUnitIdList(){
        return this.unitIdList;
    }
}