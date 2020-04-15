package csie.ntut.edu.tw.timelog.cucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import csie.ntut.edu.tw.timelog.TimelogApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = TimelogApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class SpringIntegrationTest {
    protected RestTemplate restTemplate = new RestTemplate();
    protected final String SERVER_HOST = "http://localhost:9000/apiv2";

    protected String getURL(String request_uri) {
        return this.SERVER_HOST + request_uri;
    }
}
