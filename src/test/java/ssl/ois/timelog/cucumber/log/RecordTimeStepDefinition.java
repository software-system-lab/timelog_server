package ssl.ois.timelog.cucumber.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.mock.web.MockHttpServletResponse;
import ssl.ois.timelog.cucumber.common.UserIDStepDefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecordTimeStepDefinition {
    @Autowired
    private MockMvc mvc;

    private MvcResult result;

    private GetLogByIdResponseBody resultLog;

    private RecordAPIRequestBody body;

    private UserIDStepDefinition userIDStepDefinition;

    public RecordTimeStepDefinition(UserIDStepDefinition userIDStepDefinition) {
        this.userIDStepDefinition = userIDStepDefinition;
    }

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Given("I {string} from {string} to {string}, the detail is {string}")
    public void i_from_to_the_detail_is(String title, String startTime, String endTime, String description) {
        this.body = new RecordAPIRequestBody();
        this.body.setTitle(title);
        this.body.setStartTime(startTime);
        this.body.setEndTime(endTime);
        this.body.setDescription(description);
    }

    @Given("Activity type {string} is selected")
    public void activity_type_is_selected(String activityName) {
        this.body.setActivityName(activityName);
    }

    @When("I record the activity to Timelog")
    public void i_record_the_activity_to_Timelog() throws Exception {
        this.body.setUserID(this.userIDStepDefinition.getUserID());
        this.result = this.mvc.perform(MockMvcRequestBuilders
            .post("/api/log/record")
            .content(asJsonString(this.body))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andReturn();
    }

    @Then("A new log is added")
    public void a_new_log_is_added() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(HttpStatus.OK.value(), this.result.getResponse().getStatus());
        assertTrue(this.result.getResponse().getContentAsString().contains("logID"));
        RecordAPIResponseBody recordResponse = new ObjectMapper().readValue(
                this.result.getResponse().getContentAsString(), RecordAPIResponseBody.class);
        assertNotNull(recordResponse.getLogID());

        GetLogByIdRequestBody getLogByIdRequestBody = new GetLogByIdRequestBody();
        MockHttpServletResponse response;

        getLogByIdRequestBody.setLogID(recordResponse.getLogID());
        response = this.mvc.perform(MockMvcRequestBuilders
                .post("/api/log/get/id")
                .content(asJsonString(getLogByIdRequestBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        this.resultLog = new ObjectMapper().readValue(response.getContentAsString(), GetLogByIdResponseBody.class);
        assertNotNull(this.resultLog);
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
