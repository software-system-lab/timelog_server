package ssl.ois.timelog.service.manager;

import java.util.Map;
import java.util.UUID;

import ssl.ois.timelog.common.MemberDTO;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.service.exception.AccountErrorException;

public interface AccountManager {
    Map<UUID,String> getBelongingTeams(String username)throws AccountErrorException;
    Map<UUID, MemberDTO> getTeamRoleRelation(String teamId)throws AccountErrorException;
    String getNameById(String id) throws AccountErrorException;
    Map<UUID,Role> getMemberRoleOfTeam(String unitID) throws AccountErrorException;
}
