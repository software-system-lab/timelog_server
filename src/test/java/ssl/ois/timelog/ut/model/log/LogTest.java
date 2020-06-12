package ssl.ois.timelog.ut.model.log;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.model.log.Log;

import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class LogTest {
    private static final double EPSILON = 0.001;

    private String startTime;
    private String endTime;
    private Log log;
    @Before
    public void setup() {
        UUID userID = UUID.fromString("c61965be-8176-4419-b289-4d52617728fb");
        String title = "Study for Design Pattern";
        String description = "Composite Pattern";
        String activityType = "Others";

        this.startTime = "2020/04/21 15:00";
        this.endTime = "2020/04/21 18:00";
        this.log = new Log(userID, title, this.startTime, this.endTime, description, activityType);
    }

    @Test
    public void getTime() {
        assertEquals(180, this.log.getMinutes());
        assertEquals(3.0, this.log.getHours(), EPSILON);
    }
}
