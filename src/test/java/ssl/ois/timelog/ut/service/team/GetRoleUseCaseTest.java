package ssl.ois.timelog.ut.service.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ssl.ois.timelog.model.team.Role;

public class GetRoleUseCaseTest {
    GetRoleUseCaseInput input;
    GetRoleUseCaseOutput output;
    GetRoleUseCase getRoleUseCase;

    @Before
    public void setup(){
        input = new GetRoleUseCaseInput();
        output = new GetRoleUseCaseOutput();
        getRoleUseCase = new GetRoleUseCase();
    }

    @Test
    public void getRoleWhoIsLeader(){
        input.setUnit(new User());
        assertEquals(Role.LEADER, getRoleUseCase(input).getRole());
    }

    @Test
    public void getRoleWhoIsMember(){
        
        assertEquals(Role.LEADER, getRoleUseCase(input).getRole());
    }

    @Test
    public void getRoleWhoIsProfessor(){
        
        assertEquals(Role.LEADER, getRoleUseCase(input).getRole());
    }

    @Test
    public void getRoleWhoIsStakeHolder(){
        
        assertEquals(Role.LEADER, getRoleUseCase(input).getRole());
    }
}