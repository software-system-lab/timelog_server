package ssl.ois.timelog.ut.model.log;

import org.junit.Before;
import org.junit.Test;
import ssl.ois.timelog.model.log.Log;

import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogTest {
    private final double epsilon = 0.001;
    private UUID userID;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private Log log;
    @Before
    public void setup() {
        this.userID = UUID.randomUUID();
        this.title = "Study for Design Pattern";
        this.startTime = "2020/04/21 15:00";
        this.endTime = "2020/04/21 18:00";
        this.description = "Composite Pattern";
        this.log = new Log(userID, this.title, this.startTime, this.endTime, this.description);
    }
    @Test
    public void testWithoutActivity() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        assertEquals(this.startTime, dateFormat.format(this.log.getStartTime()));
        assertEquals(this.endTime, dateFormat.format(this.log.getEndTime()));
        assertEquals("Others", this.log.getActivityID());
    }

    @Test
    public void getTime() {
        assertEquals(180, this.log.getMinutes());
        assertEquals(3.0, this.log.getHours(), epsilon);
    }
}
