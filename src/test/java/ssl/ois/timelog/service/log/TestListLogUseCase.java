package ssl.ois.timelog.service.log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.exception.AccountErrorException;
import ssl.ois.timelog.exception.DatabaseErrorException;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.list.ListLogUseCase;
import ssl.ois.timelog.service.log.list.ListLogUseCaseInput;
import ssl.ois.timelog.service.log.list.ListLogUseCaseOutput;

import java.text.ParseException;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestListLogUseCase {
    @Autowired
    private ListLogUseCase listLogUseCase;

    @MockBean
    private LogRepository repository;

    @Before
    public void set_up() throws ParseException, DatabaseErrorException {
        Mockito.when(repository.findByPeriod(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenAnswer(i -> Collections.singletonList( new Log(
                "28f47932-3ad6-44bc-a043-98ce97cf4d11",
                i.getArgument(0),
                "title",
                SqlDateTimeConverter.toDate("2021-10-19 12:00:00"),
                SqlDateTimeConverter.toDate("2021-10-19 13:00:00"),
                "",
                "00f9be59-11f5-4bf9-851f-6ea0ecfc5842",
                "Other",
                "b0be521b-4104-4475-ac9d-2120a957ecd0"
        )));
    }

    @Test
    public void test_full_edit_log() throws DatabaseErrorException, ParseException, AccountErrorException {
        ListLogUseCaseInput input = new ListLogUseCaseInput();
        input.setUnitId("b0be521b-4104-4475-ac9d-2120a957ecd0");
        input.setStartDate("2021/10/18");
        input.setEndDate("2021/10/19");
        ListLogUseCaseOutput output = new ListLogUseCaseOutput();

        listLogUseCase.execute(input, output);

        Mockito.verify(repository).findByPeriod(Mockito.eq("b0be521b-4104-4475-ac9d-2120a957ecd0"), Mockito.eq("2021/10/18"), Mockito.eq("2021/10/20"));

        Log log = output.getLogs().get(0);

        Assert.assertEquals("28f47932-3ad6-44bc-a043-98ce97cf4d11", log.getId());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", log.getUnitId());
        Assert.assertEquals("title", log.getTitle());
        Assert.assertEquals("2021-10-19 12:00:00", SqlDateTimeConverter.toString(log.getStartTime()));
        Assert.assertEquals("2021-10-19 13:00:00", SqlDateTimeConverter.toString(log.getEndTime()));
        Assert.assertEquals("", log.getDescription());
        Assert.assertEquals("00f9be59-11f5-4bf9-851f-6ea0ecfc5842", log.getActivityTypeId());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", log.getCreateBy());
    }
}
