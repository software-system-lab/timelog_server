package ssl.ois.timelog;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.mock.web.MockHttpServletResponse;
import ssl.ois.timelog.service.log.LogRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class RecordTimeStepDefinition {

    @Autowired
    private MockMvc mvc;

    private MvcResult result;

    private GetLogByIdResponseBody resultLog;

    private RecordAPIRequestBody body;

    private RecordAPIResponseBody recordResponse;

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
    public void i_from_to_the_detail_is(String title, String startTime, String endTime, String description) {
        this.body.setTitle(title);
        this.body.setStartTime(startTime);
        this.body.setEndTime(endTime);
        this.body.setDescription(description);
    }

    @Given("No activity type has been selected")
    public void no_activity_type_has_been_selected() {

    }

    @When("I record the activity to Timelog")
    public void i_record_the_activity_to_Timelog() throws Exception {
        this.result = this.mvc.perform(MockMvcRequestBuilders
            .post("/api/log/record")
            .content(asJsonString(this.body))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @Then("I get the ID of this log")
    public void i_get_the_ID_of_this_log() throws Exception {
        assertEquals(HttpStatus.OK.value(), this.result.getResponse().getStatus());
        assertTrue(this.result.getResponse().getContentAsString().contains("logID"));
        recordResponse = new ObjectMapper().readValue(
            this.result.getResponse().getContentAsString(), RecordAPIResponseBody.class);
        assertNotNull(this.recordResponse.getLogID());
    }

    @Then("I can get the complete log with this log ID")
    public void i_can_get_the_complete_log_with_this_log_ID() throws Exception{
        GetLogByIdRequestBody body = new GetLogByIdRequestBody();
        MockHttpServletResponse response;

        body.setLogID(this.recordResponse.getLogID());
        try {
            response = this.mvc.perform(MockMvcRequestBuilders
                        .post("/api/log/get/id")
                        .content(asJsonString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

            this.resultLog = new ObjectMapper().readValue(response.getContentAsString(), GetLogByIdResponseBody.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        

    }

    @Then("This log has title {string}")
    public void this_log_has_title(String title) {
        assertEquals(title, this.resultLog.getTitle());
    }

    @Then("This log has start time with {string}")
    public void this_log_has_start_time_with(String startTime) {
        assertEquals(startTime, this.resultLog.getStartTime());
    }

    @Then("This log has end time with {string}")
    public void this_log_has_end_time_with(String endTime) {
        assertEquals(endTime, this.resultLog.getEndTime());
    }

    @Then("This log has detail {string}")
    public void this_log_has_detail(String description) {
        assertEquals(description, this.resultLog.getDescription());
    }

    @Then("This log has activity type {string}")
    public void this_log_has_activity_type(String activityType) {
        assertEquals(activityType, this.resultLog.getActivityType());
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

class RecordAPIResponseBody {
    private String logID;

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getLogID() {
        return this.logID;
    }
}

class GetLogByIdRequestBody{
    private String logID;

    public void setLogID(String logID){
        this.logID = logID;
    }

    public String getLogID(){
        return this.logID;
    }

}

class GetLogByIdResponseBody{
    private String logId;
    private String title;
    private String startTime;
    private String endTime;
    private String activityType;
    private String description;

    public void setLogId(String logId){
        this.logId = logId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public void setActivityType(String activityType){
        this.activityType = activityType;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getLogId(){
        return this.logId;
    }

    public String getTitle(){
        return this.title;
    }

    public String getStartTime(){
        return this.startTime;
    }

    public String getEndTime(){
        return this.endTime;
    }

    public String getActivityType(){
        return this.activityType;
    }

    public String getDescription(){
        return this.description;
    }

}
