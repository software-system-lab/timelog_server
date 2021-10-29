package ssl.ois.timelog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.service.activity_type.add.AddActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.edit.EditActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.list.ListActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.remove.RemoveActivityTypeUseCase;
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

    @Bean(name="listLogUseCase")
    public ListLogUseCase listLogUseCase(){
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

    @Bean(name="addActivityTypeUseCase")
    public AddActivityTypeUseCase addActivityTypeUseCase(){
        return new AddActivityTypeUseCase(activityTypeRepository);
    }

    @Bean(name="editActivityTypeUseCase")
    public EditActivityTypeUseCase editActivityTypeUseCase(){
        return new EditActivityTypeUseCase(activityTypeRepository);
    }

    @Bean(name="removeActivityTypeUseCase")
    public RemoveActivityTypeUseCase removeActivityTypeUseCase(){
        return new RemoveActivityTypeUseCase(activityTypeRepository);
    }

    @Bean(name="listActivityTypeUseCase")
    public ListActivityTypeUseCase listActivityTypeUseCase(){
        return new ListActivityTypeUseCase(activityTypeRepository);
    }
}
