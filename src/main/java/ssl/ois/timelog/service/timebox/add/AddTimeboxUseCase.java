package ssl.ois.timelog.service.timebox.add;

import org.springframework.stereotype.Service;

import ssl.ois.timelog.model.connect.UnitInterface;
import ssl.ois.timelog.model.timebox.Timebox;
import ssl.ois.timelog.service.exception.DatabaseErrorException;
import ssl.ois.timelog.service.repository.user.UnitRepository;

@Service
public class AddTimeboxUseCase {
    private UnitRepository unitRepository;

    public AddTimeboxUseCase(UnitRepository unitRepository){
        this.unitRepository = unitRepository;
    }
    
    public void execute(AddTimeboxUseCaseInput input, AddTimeboxUseCaseOutput output)
            throws DatabaseErrorException{
        UnitInterface user = this.unitRepository.findByUserID(input.getUserID());
        Timebox timebox = new Timebox(input.getTitle(), input.getStartDate(), input.getEndDate());
//        user.addTimebox(timebox);

//        this.userRepository.save(user);
        
    }
}