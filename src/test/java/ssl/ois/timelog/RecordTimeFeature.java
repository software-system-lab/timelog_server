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

    private GetLogByTitleResponseBody resultLog;

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

    @Given("No activity type has been selected")
    public void no_activity_type_has_been_selected() {

    }

    @When("I record the activity to the Timelog")
    public void i_record_the_activity_to_the_Timelog() throws Exception {
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

    @Then("I will have a log with title {string}")
    public void i_will_have_a_log_with_title(String title) throws Exception{
        GetLogByTitleRequestBody body = new GetLogByTitleRequestBody();

        body.setUserID(this.body.getUserID());
        body.setTitle(title);
        MvcResult response = this.mvc.perform(MockMvcRequestBuilders
                        .post("/log/get/title")
                        .content(asJsonString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse();

        this.resultLog = new ObjectMapper().readValue(response.getContentAsString(), GetLogByTitleResponseBody.class);

        assertEquals(title, this.resultLog.getTitle());

    }

    @Then("The log has the end time with {string}")
    public void the_log_has_the_end_time_with(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The log has the detail {string}")
    public void the_log_has_the_detail(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The log has the activity type {string}")
    public void the_log_has_the_activity_type(String string) {
        // Write code here that turns the phrase above into concrete actions

        throw new io.cucumber.java.PendingException();
    }


    @Then("The log has the start time with {string}")
    public void the_log_has_the_start_time_with(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
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

class GetLogByTitleRequestBody{
    private String userID;
    private String title;

    public void setUserID(String userID){
        this.userID = userID;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUserID(){
        return this.userID;
    }

    public String getTitle(){
        return this.title;
    }

}

class GetLogByTitleResponseBody{
    private String title;
    private String startTime;
    private String endTime;
    private String activityType;
    private String description;

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
