package ssl.ois.timelog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.service.log.add.AddLogUseCase;
import ssl.ois.timelog.service.log.edit.EditLogUseCase;
import ssl.ois.timelog.service.log.list.ListLogUseCase;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCase;

@Configuration
@ComponentScan
public class UseCaseConfig {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Bean(name="listTeamLogUseCase")
    public ListLogUseCase listTeamLogUseCase(){
        return new ListLogUseCase(logRepository);
    }

    @Bean(name="addLogUseCase")
    public AddLogUseCase addLogUseCase(){
        return new AddLogUseCase(logRepository);
    }

    @Bean(name="editLogUseCase")
    public EditLogUseCase editLogUseCase() {
        return new EditLogUseCase(logRepository);
    }

    @Bean(name="removeLogUseCase")
    public RemoveLogUseCase removeLogUseCase() {
        return new RemoveLogUseCase(logRepository);
    }
}
