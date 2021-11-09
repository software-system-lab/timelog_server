package ssl.ois.timelog.service.activity_type;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.exception.activity.DuplicateActivityTypeException;
import ssl.ois.timelog.exception.activity.GetActivityTypeErrorException;
import ssl.ois.timelog.exception.activity.SaveActivityTypeErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;
import ssl.ois.timelog.service.activity_type.add.AddActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.add.AddActivityTypeUseCaseInput;

import java.text.ParseException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAddActivityTypeUseCase {
    @Autowired
    private AddActivityTypeUseCase addActivityTypeUseCase;

    @MockBean
    private ActivityTypeRepository repository;

    @Test
    public void test_create_new_activity_log() throws GetActivityTypeErrorException, DuplicateActivityTypeException, ParseException, SaveActivityTypeErrorException {
        Mockito.when(repository.findByNameAndUnitId(Mockito.anyString(), Mockito.anyString())).thenAnswer(i -> {
            throw new GetActivityTypeErrorException(i.getArgument(0));
        });

        AddActivityTypeUseCaseInput input = new AddActivityTypeUseCaseInput();
        input.setActivityName("LabProject");
        input.setUnitId("b0be521b-4104-4475-ac9d-2120a957ecd0");
        input.setEnable(true);
        input.setPrivate(false);

        addActivityTypeUseCase.execute(input);

        ArgumentCaptor<ActivityType> argument = ArgumentCaptor.forClass(ActivityType.class);

        Mockito.verify(repository).addActivityType(argument.capture());
        Mockito.verify(repository).findByNameAndUnitId(Mockito.eq("LabProject"), Mockito.eq("b0be521b-4104-4475-ac9d-2120a957ecd0"));

        ActivityType result = argument.getValue();

        Assert.assertEquals(4, result.getId().version());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId().toString());
        Assert.assertEquals("LabProject", result.getActivityName());
        Assert.assertTrue(result.isEnable());
        Assert.assertFalse(result.isDeleted());
        Assert.assertFalse(result.isPrivate());
    }

    @Test
    public void test_reopen_deleted_activity_log() throws GetActivityTypeErrorException, DuplicateActivityTypeException, ParseException, SaveActivityTypeErrorException {
        Mockito.when(repository.findByNameAndUnitId(Mockito.anyString(), Mockito.anyString())).thenAnswer(i -> new ActivityType(
                UUID.fromString("28f47932-3ad6-44bc-a043-98ce97cf4d11"),
                i.getArgument(0),
                UUID.fromString(i.getArgument(1)),
                true,
                false,
                true
        ));

        AddActivityTypeUseCaseInput input = new AddActivityTypeUseCaseInput();
        input.setActivityName("LabProject");
        input.setUnitId("b0be521b-4104-4475-ac9d-2120a957ecd0");
        input.setEnable(true);
        input.setPrivate(false);

        addActivityTypeUseCase.execute(input);

        ArgumentCaptor<ActivityType> argument = ArgumentCaptor.forClass(ActivityType.class);

        Mockito.verify(repository).updateActivityType(argument.capture());
        Mockito.verify(repository).findByNameAndUnitId(Mockito.eq("LabProject"), Mockito.eq("b0be521b-4104-4475-ac9d-2120a957ecd0"));

        ActivityType result = argument.getValue();

        Assert.assertEquals("28f47932-3ad6-44bc-a043-98ce97cf4d11", result.getId().toString());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId().toString());
        Assert.assertEquals("LabProject", result.getActivityName());
        Assert.assertTrue(result.isEnable());
        Assert.assertFalse(result.isDeleted());
        Assert.assertFalse(result.isPrivate());
    }

    @Test
    public void test_duplicate_activity_log_throw() throws GetActivityTypeErrorException, DuplicateActivityTypeException, ParseException, SaveActivityTypeErrorException {
        Mockito.when(repository.findByNameAndUnitId(Mockito.anyString(), Mockito.anyString())).thenAnswer(i -> new ActivityType(
                UUID.fromString("28f47932-3ad6-44bc-a043-98ce97cf4d11"),
                i.getArgument(0),
                UUID.fromString(i.getArgument(1)),
                true,
                false,
                false
        ));

        AddActivityTypeUseCaseInput input = new AddActivityTypeUseCaseInput();
        input.setActivityName("LabProject");
        input.setUnitId("b0be521b-4104-4475-ac9d-2120a957ecd0");
        input.setEnable(true);
        input.setPrivate(false);

        Assert.assertThrows(DuplicateActivityTypeException.class, () -> addActivityTypeUseCase.execute(input));

        Mockito.verify(repository).findByNameAndUnitId(Mockito.eq("LabProject"), Mockito.eq("b0be521b-4104-4475-ac9d-2120a957ecd0"));
    }
}
