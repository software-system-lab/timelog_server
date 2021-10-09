package ssl.ois.timelog.ut.service.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.exception.activity.ActivityTypeNotExistException;
import ssl.ois.timelog.service.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.service.exception.role.GetRoleErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;
import ssl.ois.timelog.service.role.get.*;

import org.junit.Before;
import org.junit.Test;

import ssl.ois.timelog.adapter.repository.memory.MemoryUnitRepository;
import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.model.team.Role;
import ssl.ois.timelog.model.team.Team;
import ssl.ois.timelog.model.user.User;

public class GetRoleUseCaseTest {
    private UnitRepository unitRepository;
    private GetRoleUseCaseInput input;
    private GetRoleUseCaseOutput output;
    private GetRoleUseCase getRoleUseCase;
    private Map<String, Unit> users;
    private Map<UUID, Role> memberRoleMap;
    private Unit team;
    private UUID memberID;              //8e0f3043-85d7-4c7b-9ac9-cfb1149270b9
    private UUID leaderID;              //5a39fabf-f969-4dfb-8878-f1b1bbe7188b
    private UUID professorID;           //7be599c9-0cb1-42f6-9ec3-95bfc446c42e
    private UUID stakeHolderID;         //d5fedb30-1489-41da-ba91-0211431d5ef3 
    private UUID teamID;                //5edd79d6-7d15-4e97-b6b4-fb8ed76e47ab

    @Before
    public void setup(){
        input = new GetRoleUseCaseInput();
        output = new GetRoleUseCaseOutput();
        this.unitRepository = new MemoryUnitRepository();
        getRoleUseCase = new GetRoleUseCase(unitRepository);
        this.users = new HashMap<>();
        this.memberRoleMap = new HashMap<>();

        memberID = UUID.fromString("8e0f3043-85d7-4c7b-9ac9-cfb1149270b9");
        leaderID = UUID.fromString("5a39fabf-f969-4dfb-8878-f1b1bbe7188b");
        professorID = UUID.fromString("7be599c9-0cb1-42f6-9ec3-95bfc446c42e");
        stakeHolderID = UUID.fromString("d5fedb30-1489-41da-ba91-0211431d5ef3");
        teamID = UUID.fromString("5edd79d6-7d15-4e97-b6b4-fb8ed76e47ab");

        users.put(memberID.toString(), new User(memberID));
        users.put(leaderID.toString(), new User(leaderID));
        users.put(professorID.toString(), new User(professorID));
        users.put(stakeHolderID.toString(), new User(stakeHolderID));

        memberRoleMap.put(memberID, Role.MEMBER);
        memberRoleMap.put(leaderID, Role.LEADER);
        memberRoleMap.put(professorID, Role.PROFESSOR);
        memberRoleMap.put(stakeHolderID, Role.STAKEHOLDER);
        team = new Team(teamID, memberRoleMap);

        try{
            unitRepository.save(team);
        }catch(ActivityTypeNotExistException | DatabaseErrorException | DuplicateActivityTypeException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void getRoleWhoIsLeader(){
        input.setTeamID(teamID);
        input.setUserID(leaderID);

        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            fail(e.getMessage());
        }

        assertEquals(Role.LEADER.toString(), output.getRole());
    }

    @Test
    public void getRoleWhoIsMember(){
        input.setTeamID(teamID);
        input.setUserID(memberID);
        
        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            fail(e.getMessage());
        }

        assertEquals(Role.MEMBER.toString(), output.getRole());
    }

    @Test
    public void getRoleWhoIsProfessor(){
        input.setTeamID(teamID);
        input.setUserID(professorID);
        
        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            fail(e.getMessage());
        }

        assertEquals(Role.PROFESSOR.toString(), output.getRole());
    }

    @Test
    public void getRoleWhoIsStakeHolder(){
        input.setTeamID(teamID);
        input.setUserID(stakeHolderID);
        
        try{
            getRoleUseCase.execute(input, output);
        }catch(GetRoleErrorException e){
            fail(e.getMessage());
        }

        assertEquals(Role.STAKEHOLDER.toString(), output.getRole());
    }
}