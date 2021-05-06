package ssl.ois.timelog.service.role.get;

import javax.management.relation.Role;

public class GetRoleUseCaseOutput {
    private Role role;

    public GetRoleUseCaseOutput(Role role){
        this.role = role;
    }

    public Role getRole(){
        return this.role;
    }

    public void setRole(Role role){
        this.role = role;
    }
}