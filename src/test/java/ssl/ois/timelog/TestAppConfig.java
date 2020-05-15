package ssl.ois.timelog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ssl.ois.timelog.adapter.repository.memory.MemoryLogRepository;
import ssl.ois.timelog.service.log.LogRepository;

@Configuration
public class TestAppConfig {
    @Bean
    @Primary
    public LogRepository getMemoryLogRepository() {
        return new MemoryLogRepository();
    }
}
