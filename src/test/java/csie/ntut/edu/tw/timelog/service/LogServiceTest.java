// package csie.ntut.edu.tw.timelog.service;

// import csie.ntut.edu.tw.timelog.model.Log;
// import csie.ntut.edu.tw.timelog.repository.LogRepository;
// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit4.SpringRunner;

// import java.sql.Timestamp;
// import java.util.Date;

// import static org.mockito.BDDMockito.given;

// @RunWith(SpringRunner.class)
// public class LogServiceTest {
//     private Log log;

//     @MockBean
//     private LogRepository logRepo;

//     @Before
//     public void setUp() {
//         this.log = new Log();
//         this.log.setUserID("U12345");
//         this.log.setTag("timelog");
//         this.log.setTitle("Server project built");
//         this.log.setDescription("I've complete building the project of timelog server");

//         Date date = new Date();
//         long time1 = date.getTime();
//         System.out.println("Time in Milliseconds: " + time1);
//         Timestamp ts1 = new Timestamp(time1);
//         System.out.println("Current Time Stamp: " + ts1);
//         this.log.setStartTime(ts1);
//         long time2 = date.getTime();
//         Timestamp ts2 = new Timestamp(time2);
//         this.log.setEndTime(ts2);
//     }

//     @Test
//     public void testLogInsert() {
//         given(this.logRepo.save(this.log)).willReturn(this.log);
//         LogService ls = new LogService(this.logRepo);
//         boolean result = ls.newLog(this.log);
//         Assert.assertTrue(result);
//     }
// }