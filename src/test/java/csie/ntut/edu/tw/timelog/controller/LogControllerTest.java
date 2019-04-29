package csie.ntut.edu.tw.timelog.controller;

import csie.ntut.edu.tw.timelog.model.Log;
import csie.ntut.edu.tw.timelog.service.LogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class LogControllerTest {
    @MockBean
    private LogService logServ;

    @Test
    public void testNewLog() {
        Log log = new Log();
        given(this.logServ.newLog(log)).willReturn(true);
        LogController ctrl = new LogController(this.logServ);
        ResponseEntity result = ctrl.newLog(log);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(log.toString(), result.getBody());
    }

    @Test
    public void testNewLogError() {
        Log error = new Log();
        given(this.logServ.newLog(error)).willReturn(false);
        LogController ctrl = new LogController(this.logServ);
        ResponseEntity result = ctrl.newLog(error);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assert.assertEquals("Message: Error", result.getBody());
    }
}
