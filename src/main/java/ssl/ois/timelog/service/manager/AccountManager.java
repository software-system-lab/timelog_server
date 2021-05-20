package ssl.ois.timelog.service.manager;

import java.util.Map;
import java.util.UUID;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.AccountErrorException;

public interface AccountManager {
    Map<UUID,String> getMemberOf(String username)throws AccountErrorException;
    Map<UUID, Role> getTeamRoleRelation(String teamName)throws AccountErrorException;
    UUID getTeamIdByTeamName(String teamName) throws AccountErrorException;
    String getNameById(String id) throws AccountErrorException;
}
