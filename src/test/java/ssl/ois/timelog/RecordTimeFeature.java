package ssl.ois.timelog;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class RecordTimeFeature {
    @Autowired
    private MockMvc mvc;

    private MvcResult result;

    private RecordAPIRequestBody body;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Given("My user ID is {string}")
    public void my_user_ID_is(String userID) {
        this.body = new RecordAPIRequestBody();
        this.body.setUserID(userID);
    }

    @Given("I {string} from {string} to {string}, the detail is {string}")
    public void i_from_to_the_detail_is(String title,
                                        String startTime,
                                        String endTime,
                                        String description) {
        this.body.setTitle(title);
        this.body.setStartTime(startTime);
        this.body.setEndTime(endTime);
        this.body.setDescription(description);
    }

    @When("I record the activity to the Timelog")
    public void i_record_the_activity_to_the_Timelog() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RecordAPIRequestBody> request = new HttpEntity<>(this.body, headers);
        this.result = this.mvc.perform(MockMvcRequestBuilders
            .post("/log/record")
            .content(asJsonString(this.body))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Then("the system should have the log record")
    public void the_system_should_have_the_log_record() throws Exception{
        assertEquals(HttpStatus.OK.value(), this.result.getResponse().getStatus());
        assertTrue(this.result.getResponse().getContentAsString().contains("logID"));
    }
}

class RecordAPIRequestBody {
    private String userID;
    private String title;
    private String activityName;
    private String startTime;
    private String endTime;
    private String description;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

//class RecordAPIResponse {
//    private String logID;
//    public String getLogID() {
//        return logID;
//    }
//
//    public void setLogID(String logID) {
//        this.logID = logID;
//    }
//}

//class GetLogAPIResponse {
//    private String logID;
//    private String title;
//    private String activityName;
//    private String startTime;
//    private String endTime;
//    private String description;
//
//    public String getLogID() {
//        return logID;
//    }
//
//    public void setLogID(String logID) {
//        this.logID = logID;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getActivityName() {
//        return activityName;
//    }
//
//    public void setActivityName(String activityName) {
//        this.activityName = activityName;
//    }
//
//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
