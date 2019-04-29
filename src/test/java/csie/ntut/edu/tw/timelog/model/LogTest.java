package csie.ntut.edu.tw.timelog.model;

import java.util.Date;
import java.sql.Timestamp;
import org.junit.Test;
import org.junit.Assert;

public class LogTest {
    @Test
    public void TestGetter() {
        Log l = new Log();
        l.setUserID("U12345");
        l.setTag("timelog");
        l.setTitle("Server project built");
        l.setDescription("I've complete building the project of timelog server");

        Date date = new Date();
        long time1 = date.getTime();
        System.out.println("Time in Milliseconds: " + time1);
        Timestamp ts1 = new Timestamp(time1);
        System.out.println("Current Time Stamp: " + ts1);
        l.setStartTime(ts1);
        long time2 = date.getTime();
        Timestamp ts2 = new Timestamp(time2);
        l.setEndTime(ts2);
        Assert.assertEquals("U12345", l.getUserID());
        Assert.assertEquals("timelog", l.getTag());
        Assert.assertEquals("Server project built", l.getTitle());
        Assert.assertEquals("I've complete building the project of timelog server", l.getDescription());
        Assert.assertEquals(ts1, l.getStartTime());
        Assert.assertEquals(ts2, l.getEndTime());
    }
}
