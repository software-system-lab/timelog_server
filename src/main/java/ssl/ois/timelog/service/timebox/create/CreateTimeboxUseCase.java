package ssl.ois.timelog.service.timebox.create;

public class CreateTimeboxUseCase {
    
    public void execute(CreateTimeboxUseCaseInput input, CreateTimeboxUseCaseOutput output){
        String title = input.getTitle();
        String startTime = input.getStartTime();
        String endTime = input.getEndTime();
    }
}