package ssl.ois.timelog.adapter.manager.directory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ssl.ois.timelog.service.manager.AccountManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashMap;

import com.google.gson.JsonObject;

import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.model.team.Role;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import ssl.ois.timelog.service.team.TeamDTO;
import ssl.ois.timelog.service.user.UserDTO;

public class DirectoryAMSManager implements AccountManager {

    private String url;
    private RestTemplate restTemplate;

    public DirectoryAMSManager(String amsProtocol, String amsUrl){
        this.url = amsProtocol + "://" + amsUrl;
        this.restTemplate = new RestTemplate();
    }


    //Get Belonging Teams
    //Returns Map of Team(UUID) as Index, TeamName(String) as value 
    public Map<UUID, String> getBelongingTeams(String username)throws AccountErrorException {
        Map<UUID,String> teamList = new HashMap<>();
        try {
            // final String requestAddress = this.url + "/team/get/memberOf";
            final String requestAddress = this.url + "/team/get/belonging-teams";
            JsonObject body = new JsonObject();
            body.addProperty("username", username);

            HttpEntity<String> req = new HttpEntity<>(body.toString());
            ResponseEntity<List<TeamDTO>> res = this.restTemplate.exchange(
                requestAddress,
                HttpMethod.POST,
                req,
                new ParameterizedTypeReference<List<TeamDTO>>() { }
            );

            for (TeamDTO team: res.getBody()) {
                String id = team.getId().replaceAll("^\"|\"$", "");
                String name = team.getName().replaceAll("^\"|\"$", "");
                teamList.put(UUID.fromString(id), name);
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new AccountErrorException(e.toString());
        } 
        return teamList;
    }

    //Get Team Member and Role
    //Returns Map of Member(UUID) as index, Role(Role) as value 
    public Map<UUID, Role> getTeamRoleRelation(String teamID) throws AccountErrorException {
        Map<UUID, Role> memberRoleMap = new HashMap<>();
        try {
            final String requestAddress = this.url + "/team/" + teamID;

            ResponseEntity<TeamDTO> res = this.restTemplate.exchange(
                requestAddress,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TeamDTO>() { }
            );
            TeamDTO teamDTO = res.getBody();
            assert teamDTO != null;

            memberRoleMap.put(UUID.fromString(teamDTO.getLeader().getUserId()), Role.LEADER);
            for (UserDTO user: teamDTO.getMembers()){
                memberRoleMap.putIfAbsent(UUID.fromString(user.getUserId()), Role.MEMBER);
            }
        } catch (RestClientException e) {
            throw new AccountErrorException(e.toString());
        } 
        return memberRoleMap;
    }

    //Get UUID From Name
    //Returns UUID of Name
    public UUID getTeamIdByTeamName(String teamName) throws AccountErrorException{
        UUID id = null;
        try {
            final String requestAddress = this.url + "/team/get/uuid/user";
            String result = this.restTemplate.postForObject(requestAddress, teamName, String.class);
            String uid = result.replaceAll("^\"|\"$", "");
            id = UUID.fromString(uid);
        } catch (RestClientException e){
            throw new AccountErrorException(e.toString());
        }
        return id;
    }

    //get any name by any id
    public String getNameById(String id) throws AccountErrorException{
        String result;
        try{
            final String requestAddress = this.url + "/get/name";
            result = this.restTemplate.postForObject(requestAddress, id, String.class).replaceAll("^\"|\"$", "");
        }catch (RestClientException e){
            throw new AccountErrorException(e.toString());
        }
        return result;
    }

    // Get Member role of Unit
    // If it is not Team, return null instead
    public Map<UUID,Role> getMemberRoleOfTeam(String unitID) throws AccountErrorException {
        Map<UUID,Role> result = new HashMap<>();
        try{
            final String requestAddress = this.url + "/team/member/role";
            List<LinkedHashMap<String,String>> request = this.restTemplate.postForObject(requestAddress, unitID, List.class);
            for(LinkedHashMap<String,String> each : request) {     
                result.put(UUID.fromString(each.get("id").replaceAll("^\"|\"$", "")),Role.valueOf(each.get("role").replaceAll("^\"|\"$", "")));
            }
        }catch (RestClientException e){
            System.out.println(e.toString());
            // throw new AccountErrorException(e.toString());
            return result;
        }
        return result;
    }
}


