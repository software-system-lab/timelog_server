package ssl.ois.timelog.adapter.manager;
import ssl.ois.timelog.service.manager.AccountManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.util.HashMap;
import ssl.ois.timelog.service.exception.AccountErrorException;
import ssl.ois.timelog.model.team.Role;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

public class DirectoryAMSManager implements AccountManager {

    private String url;
    private RestTemplate restTemplate;

    public DirectoryAMSManager(String amsHost, String amsPort){
        this.url = "http://" + amsHost + ":" + amsPort;
        this.restTemplate = new RestTemplate();

    }


    //Get Belonging Teams
    //Returns Map of Team(UUID) as Index, TeamName(String) as value 
    public Map<UUID,String> getMemberOf(String username)throws AccountErrorException {
        Map<UUID,String> teamList = new HashMap<>();
        try {
            final String requestAddress = this.url + "/team/get/memberOf";
            List<LinkedHashMap<String,String>> result = this.restTemplate.postForObject(requestAddress, username, List.class);
            for(LinkedHashMap<String,String> each : result) {
                String uid = each.get("id").replaceAll("^\"|\"$", "");

                teamList.put(UUID.fromString(uid),each.get("name"));
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new AccountErrorException();
        } 
        return teamList;
    }

    //Get Team Member and Role
    //Returns Map of Member(UUID) as index, Role(Role) as value 
    public Map<UUID, Role> getTeamRoleRelation(String teamName) throws AccountErrorException {
        Map<UUID, Role> memberRoleMap = new HashMap<>();
        try {
            final String requestAddress = this.url + "/team/get/members";
            List<String> result = this.restTemplate.postForObject(requestAddress, teamName, List.class);
            memberRoleMap.put(this.getLeader(teamName), Role.LEADER);

            for(String uid :result){
                uid = uid.replaceAll("^\"|\"$", "");
                UUID userID = UUID.fromString(uid);
                if(memberRoleMap.get(userID)==null){
                    memberRoleMap.put(userID, Role.MEMBER);
                }
            }
        } catch (RestClientException e) {
            throw new AccountErrorException();
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
            throw new AccountErrorException();
        }
        return id;
    }

    public String getNameById(String id) throws AccountErrorException{
        String result;
        try{
            final String requestAddress = this.url + "/team/get/name";
            result = this.restTemplate.postForObject(requestAddress, id, String.class);
        }catch (RestClientException e){
            throw new AccountErrorException();
        }
        return result;
    }

    //Get Team's Leader
    //Returns UUID of Leader
    private UUID getLeader(String teamName) {
        UUID leaderID = null;
        
        try {
            final String requestAddress = this.url + "/team/get/leader";
            String result = this.restTemplate.postForObject(requestAddress, teamName, String.class);

            leaderID = this.getUidFromName(result.replace("\"", ""));
        } catch (RestClientException e) {
            //throw exception
        } 
        return leaderID;
    }

    //Get User's UUID
    //Returns UUID of the user
    private UUID getUidFromName(String cn) {
        UUID id = null;
        try {
            final String requestAddress = this.url + "/team/get/uuid/user";
            String result = this.restTemplate.postForObject(requestAddress, cn, String.class);
            String uid = result.replaceAll("^\"|\"$", "");
            id = UUID.fromString(uid);
        } catch (RestClientException e) {
            //throw exception
        } 
        return id;
    }
}


