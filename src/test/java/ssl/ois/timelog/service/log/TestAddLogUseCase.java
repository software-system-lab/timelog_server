package ssl.ois.timelog.service.log;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.common.SqlDateTimeConverter;
import ssl.ois.timelog.exception.log.SaveLogErrorException;
import ssl.ois.timelog.model.log.Log;
import ssl.ois.timelog.service.log.add.AddLogUseCase;
import ssl.ois.timelog.service.log.add.AddLogUseCaseInput;

import java.text.ParseException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAddLogUseCase {
    @Autowired
    private AddLogUseCase addLogUseCase;

    @MockBean
    private LogRepository repository;

    @Test
    public void test_add_log() throws SaveLogErrorException, ParseException {
        AddLogUseCaseInput input = new AddLogUseCaseInput(
                "b0be521b-4104-4475-ac9d-2120a957ecd0",
                "title",
                "2021-10-19 12:00:00",
                "2021-10-19 13:00:00",
                "",
                "00f9be59-11f5-4bf9-851f-6ea0ecfc5842",
                "b0be521b-4104-4475-ac9d-2120a957ecd0"
        );

        addLogUseCase.execute(input);

        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);

        Mockito.verify(repository).addLog(argument.capture());

        Log result = argument.getValue();

        Assert.assertEquals(4, UUID.fromString(result.getId()).version());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId());
        Assert.assertEquals("title", result.getTitle());
        Assert.assertEquals("2021-10-19 12:00:00", SqlDateTimeConverter.toString(result.getStartTime()));
        Assert.assertEquals("2021-10-19 13:00:00", SqlDateTimeConverter.toString(result.getEndTime()));
        Assert.assertEquals("", result.getDescription());
        Assert.assertEquals("00f9be59-11f5-4bf9-851f-6ea0ecfc5842", result.getActivityTypeId());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getCreateBy());
    }
}
