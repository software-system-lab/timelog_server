package csie.ntut.edu.tw.timelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class TimelogApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimelogApplication.class, args);
	}

}
