package ssl.ois.timelog.service.log.remove;

import ssl.ois.timelog.exception.log.RemoveLogException;
import ssl.ois.timelog.adapter.repository.log.LogRepository;

public class RemoveLogUseCase {
    private final LogRepository logRepository;

    public RemoveLogUseCase(LogRepository logRepository) {this.logRepository = logRepository;}

    public void execute(RemoveLogUseCaseInput input) throws RemoveLogException {
        logRepository.removeLog(input.getId());
    }
}
