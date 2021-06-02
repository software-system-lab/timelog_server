package ssl.ois.timelog.service.activity.type.list;

import java.util.List;

public class ListActivityTypeUseCaseInput {
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