package ssl.ois.timelog;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class StepDefinitions {
    @Autowired
    private MockMvc mvc;

    private MvcResult result;

    private RecordAPIRequestBody body;
    private RecordAPIResponse response;

    @Given("I {string} for {string} from {string} to {string}, the detail is {string}")
    public void i_for_from_to_the_detail_is(String title,String activityName,String startTime,String endTime,String description) {
        this.body = new RecordAPIRequestBody(title, activityName, startTime, endTime, description);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("I record the activity")
    public void i_record_the_activity() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecordAPIRequestBody> request = new HttpEntity<>(this.body, headers);
        this.mvc.perform(MockMvcRequestBuilders
            .post("/log/record")
            .content(asJsonString(this.body))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.logID").exists());
    }

//    @Then("the system should have the log record")
//    public void the_system_should_have_the_log_record() {
//        throw new io.cucumber.java.PendingException();
//        GetLogAPIResponse resultObject = this.restTemplate.getForObject(this.getURL("/log/get/{id}"),
//                                       GetLogAPIResponse.class,
//                                        this.response.getLogID());
//        assertEquals(this.response.getLogID(), resultObject.getLogID());
//        assertEquals(this.body.getTitle(), resultObject.getTitle());
//        assertEquals(this.body.getActivityName(), resultObject.getActivityName());
//        assertEquals(this.body.getStartTime(), resultObject.getStartTime());
//        assertEquals(this.body.getEndTime(), resultObject.getEndTime());
//        assertEquals(this.body.getDescription(), resultObject.getDescription());
//    }
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
