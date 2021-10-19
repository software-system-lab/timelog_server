package ssl.ois.timelog.service.log.edit;

import org.junit.Assert;
import org.junit.Before;
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
import ssl.ois.timelog.exception.log.GetLogErrorException;
import ssl.ois.timelog.exception.log.SaveLogErrorException;
import ssl.ois.timelog.model.log.Log;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEditUseCase {
    @Autowired
    private EditLogUseCase editLogUseCase;

    @MockBean
    private LogRepository repository;

    @Before
    public void set_up() throws GetLogErrorException, ParseException {
        Mockito.when(repository.findById(Mockito.anyString())).thenAnswer(i -> {
            return new Log(
                    i.getArgument(0),
                    "b0be521b-4104-4475-ac9d-2120a957ecd0",
                    "title",
                    SqlDateTimeConverter.toDate("2021-10-19 12:00:00"),
                    SqlDateTimeConverter.toDate("2021-10-19 13:00:00"),
                    "",
                    "00f9be59-11f5-4bf9-851f-6ea0ecfc5842",
                    "Other",
                    "b0be521b-4104-4475-ac9d-2120a957ecd0"
            );
        });
    }

    @Test
    public void test_full_edit_log() throws SaveLogErrorException, ParseException, GetLogErrorException {
        EditLogUseCaseInput input = new EditLogUseCaseInput(
                "28f47932-3ad6-44bc-a043-98ce97cf4d11",
                "new title",
                "2021-10-19 13:00:00",
                "2021-10-19 14:00:00",
                "new desc",
                "e40d5c53-9819-4146-9549-760d2a467271"
        );

        editLogUseCase.execute(input);

        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);

        Mockito.verify(repository).updateLog(argument.capture());

        Log result = argument.getValue();

        Assert.assertEquals("28f47932-3ad6-44bc-a043-98ce97cf4d11", result.getId());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId());
        Assert.assertEquals("new title", result.getTitle());
        Assert.assertEquals("2021-10-19 13:00:00", SqlDateTimeConverter.toString(result.getStartTime()));
        Assert.assertEquals("2021-10-19 14:00:00", SqlDateTimeConverter.toString(result.getEndTime()));
        Assert.assertEquals("new desc", result.getDescription());
        Assert.assertEquals("e40d5c53-9819-4146-9549-760d2a467271", result.getActivityTypeId());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getCreateBy());
    }

    @Test
    public void test_partial_edit_log() throws SaveLogErrorException, ParseException, GetLogErrorException {
        EditLogUseCaseInput input = new EditLogUseCaseInput();

        input.setId("28f47932-3ad6-44bc-a043-98ce97cf4d11");

        editLogUseCase.execute(input);

        ArgumentCaptor<Log> argument = ArgumentCaptor.forClass(Log.class);

        Mockito.verify(repository).updateLog(argument.capture());

        Log result = argument.getValue();

        Assert.assertEquals("28f47932-3ad6-44bc-a043-98ce97cf4d11", result.getId());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId());
        Assert.assertEquals("title", result.getTitle());
        Assert.assertEquals("2021-10-19 12:00:00", SqlDateTimeConverter.toString(result.getStartTime()));
        Assert.assertEquals("2021-10-19 13:00:00", SqlDateTimeConverter.toString(result.getEndTime()));
        Assert.assertEquals("", result.getDescription());
        Assert.assertEquals("00f9be59-11f5-4bf9-851f-6ea0ecfc5842", result.getActivityTypeId());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getCreateBy());
    }
}
