package ssl.ois.timelog.service.log.remove;

import ssl.ois.timelog.service.repository.log.LogRepository;

public class RemoveLogUseCase {
    private LogRepository logRepository;

    public RemoveLogUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void execute(RemoveLogUseCaseInput input, RemoveLogUseCaseOutput output) {
        Boolean succeed = this.logRepository.removeByID(input.getLogID());
        output.setResult(succeed);
    }
}