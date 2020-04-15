package csie.ntut.edu.tw.timelog.cucumber;

import static org.junit.Assert.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class RecordTimeStepDefinition extends SpringIntegrationTest {
    private int hours;
    private int storedHours;

    private RecordAPIRequestBody body;
    private RecordAPIResponse response;

    @Given("I {string} for {string} from {string} to {string}, the detail is {string}")
    public void i_for_from_to_the_detail_is(String title,
                                            String activityName,
                                            String startTime,
                                            String endTime,
                                            String description) {
        this.body = new RecordAPIRequestBody(title, activityName, startTime, endTime, description);
    }

    @When("I record the activity")
    public void i_record_the_activity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecordAPIRequestBody> request = new HttpEntity<>(this.body, headers);
        this.response = this.restTemplate.postForObject(this.getURL("/log/create"),
                                                        request,
                                                        RecordAPIResponse.class);
    }

    @Then("the system should have the log record")
    public void the_system_should_have_the_log_record() {
        assertNotNull(this.response.getLogID());
        assertNotEquals("", this.response.getLogID());
        throw new io.cucumber.java.PendingException();
//        GetLogAPIResponse resultObject = this.restTemplate.getForObject(this.getURL("/log/get/{id}"),
//                                       GetLogAPIResponse.class,
//                                        this.response.getLogID());
//        assertEquals(this.response.getLogID(), resultObject.getLogID());
//        assertEquals(this.body.getTitle(), resultObject.getTitle());
//        assertEquals(this.body.getActivityName(), resultObject.getActivityName());
//        assertEquals(this.body.getStartTime(), resultObject.getStartTime());
//        assertEquals(this.body.getEndTime(), resultObject.getEndTime());
//        assertEquals(this.body.getDescription(), resultObject.getDescription());
    }
}

class RecordAPIRequestBody {
    private String title;
    private String activityName;
    private String startTime;
    private String endTime;
    private String description;

    public RecordAPIRequestBody(String title, String activityName, String startTime, String endTime, String description) {
        this.title = title;
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

class RecordAPIResponse {
    private String logID;
    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }
}

class GetLogAPIResponse {
    private String logID;
    private String title;
    private String activityName;
    private String startTime;
    private String endTime;
    private String description;

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
