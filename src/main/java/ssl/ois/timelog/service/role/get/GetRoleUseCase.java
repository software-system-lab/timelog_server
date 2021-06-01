package ssl.ois.timelog.service.role.get;
import ssl.ois.timelog.service.exception.role.GetRoleErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.connect.Unit;
import ssl.ois.timelog.service.exception.DatabaseErrorException;

@Service
public class GetRoleUseCase {
    private UnitRepository unitRepository;

    public GetRoleUseCase(UnitRepository unitRepository){
        this.unitRepository = unitRepository;
    }

    public void execute(GetRoleUseCaseInput input, GetRoleUseCaseOutput output) throws GetRoleErrorException{

        try{
            Unit team = unitRepository.findByUnitID(input.getTeamID().toString());
            if(team.getMemberRoleMap().get(input.getUserID()) == null){
                throw new GetRoleErrorException("User is not in the Team!");
            }
            output.setRole(team.getMemberRoleMap().get(input.getUserID()));
        } catch (DatabaseErrorException e){
            throw new GetRoleErrorException(e.toString());
        }

        // try{
        //     //getmap
        //     //search map
        //     //get role
        //     Role role = this.unitRepository.getRole(input.getUserID().toString(), input.getTeamID().toString());
        //     output.setRole(role);
        // } catch (DatabaseErrorException e) {
        //     throw new GetRoleErrorException(e.toString());
        // }
        
    }
}