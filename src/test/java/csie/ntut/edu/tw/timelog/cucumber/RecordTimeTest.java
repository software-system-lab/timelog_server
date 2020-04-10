package csie.ntut.edu.tw.timelog.cucumber;
import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class RecordTimeTest {
    private int hours;
    private int storedHours;

    @Given("I {string} for {string} from {string} to {string}, the detail is {string}")
    public void i_for_from_to_the_detail_is(String title, String activityName, String startTime, String endTime, String description) {
        throw new io.cucumber.java.PendingException();
    }

    @When("I record the activity")
    public void i_record_the_activity() {
        throw new io.cucumber.java.PendingException();
    }

    @Then("the system should have the log record")
    public void the_system_should_have_the_log_record() {
        throw new io.cucumber.java.PendingException();
    }
}
