package ssl.ois.timelog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ssl.ois.timelog.controller.memory.MemoryLogRepository;
import ssl.ois.timelog.controller.memory.MemoryUserRepository;
import ssl.ois.timelog.service.log.LogRepository;
import ssl.ois.timelog.service.user.UserRepository;

@Configuration
@ComponentScan
public class AppConfig {
    @Bean
    public LogRepository getLogRepository() {
        return new MemoryLogRepository();
    }

    @Bean
    public UserRepository getUserRepository() {
        return new MemoryUserRepository();
    }
}
