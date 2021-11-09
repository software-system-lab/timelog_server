package ssl.ois.timelog.service.activity_type;

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
import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;
import ssl.ois.timelog.service.activity_type.edit.EditActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.edit.EditActivityTypeUseCaseInput;

import java.text.ParseException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEditActivityTypeUseCase {
    @Autowired
    private EditActivityTypeUseCase editActivityTypeUseCase;

    @MockBean
    private ActivityTypeRepository repository;

    @Before
    public void set_up() throws GetActivityTypeErrorException {
        Mockito.when(repository.findById(Mockito.anyString())).thenAnswer(i -> new ActivityType(
                UUID.fromString(i.getArgument(0)),
                "LabProject",
                UUID.fromString("b0be521b-4104-4475-ac9d-2120a957ecd0"),
                true,
                false,
                false
        ));
    }

    @Test
    public void test_full_edit_activity_type() throws ParseException, GetActivityTypeErrorException, SaveActivityTypeErrorException {
        EditActivityTypeUseCaseInput input = new EditActivityTypeUseCaseInput();
        input.setId("28f47932-3ad6-44bc-a043-98ce97cf4d11");
        input.setActivityName("LabDuty");
        input.setEnable(false);
        input.setPrivate(true);

        editActivityTypeUseCase.execute(input);

        ArgumentCaptor<ActivityType> argument = ArgumentCaptor.forClass(ActivityType.class);

        Mockito.verify(repository).updateActivityType(argument.capture());

        ActivityType result = argument.getValue();

        Assert.assertEquals("28f47932-3ad6-44bc-a043-98ce97cf4d11", result.getId().toString());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId().toString());
        Assert.assertEquals("LabDuty", result.getActivityName());
        Assert.assertFalse(result.isEnable());
        Assert.assertFalse(result.isDeleted());
        Assert.assertTrue(result.isPrivate());
    }

    @Test
    public void test_partial_edit_log() throws ParseException, GetActivityTypeErrorException, SaveActivityTypeErrorException {
        EditActivityTypeUseCaseInput input = new EditActivityTypeUseCaseInput();
        input.setId("28f47932-3ad6-44bc-a043-98ce97cf4d11");

        editActivityTypeUseCase.execute(input);

        ArgumentCaptor<ActivityType> argument = ArgumentCaptor.forClass(ActivityType.class);

        Mockito.verify(repository).updateActivityType(argument.capture());

        ActivityType result = argument.getValue();

        Assert.assertEquals("28f47932-3ad6-44bc-a043-98ce97cf4d11", result.getId().toString());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId().toString());
        Assert.assertEquals("LabProject", result.getActivityName());
        Assert.assertTrue(result.isEnable());
        Assert.assertFalse(result.isDeleted());
        Assert.assertFalse(result.isPrivate());
    }
}
