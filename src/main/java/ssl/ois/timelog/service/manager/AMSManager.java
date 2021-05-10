package ssl.ois.timelog.service.manager;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.AMSErrorException;

public interface AMSManager {
    Map<UUID,String>  getMemberOf(String username)throws AMSErrorException;
    Map<UUID, Role> getTeamRoleRelation(String teamId)throws AMSErrorException; 
}
