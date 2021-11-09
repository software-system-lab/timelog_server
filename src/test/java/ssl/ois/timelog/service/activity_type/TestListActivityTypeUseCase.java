package ssl.ois.timelog.service.activity_type;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ssl.ois.timelog.adapter.repository.activity_type.ActivityTypeRepository;
import ssl.ois.timelog.exception.DatabaseErrorException;
import ssl.ois.timelog.model.activity_type.ActivityType;
import ssl.ois.timelog.service.activity_type.list.ListActivityTypeUseCase;
import ssl.ois.timelog.service.activity_type.list.ListActivityTypeUseCaseInput;
import ssl.ois.timelog.service.activity_type.list.ListActivityTypeUseCaseOutput;

import java.util.Collections;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestListActivityTypeUseCase {
    @Autowired
    private ListActivityTypeUseCase listActivityTypeUseCase;

    @MockBean
    private ActivityTypeRepository repository;

    @Before
    public void set_up() throws DatabaseErrorException {
        Mockito.when(repository.findByUnitId(Mockito.anyString())).thenAnswer(i -> Collections.singletonList(new ActivityType(
                UUID.fromString("28f47932-3ad6-44bc-a043-98ce97cf4d11"),
                "LabProject",
                UUID.fromString(i.getArgument(0)),
                true,
                false,
                false
        )));
    }

    @Test
    public void test_list_activity_type () throws DatabaseErrorException {
        ListActivityTypeUseCaseInput input = new ListActivityTypeUseCaseInput();
        input.setUnitId("b0be521b-4104-4475-ac9d-2120a957ecd0");
        ListActivityTypeUseCaseOutput output = new ListActivityTypeUseCaseOutput();

        listActivityTypeUseCase.execute(input, output);

        Mockito.verify(repository).findByUnitId(Mockito.eq("b0be521b-4104-4475-ac9d-2120a957ecd0"));

        ActivityType result = output.getActivityTypes().get(0);

        Assert.assertEquals("28f47932-3ad6-44bc-a043-98ce97cf4d11", result.getId().toString());
        Assert.assertEquals("b0be521b-4104-4475-ac9d-2120a957ecd0", result.getUnitId().toString());
        Assert.assertEquals("LabProject", result.getActivityName());
        Assert.assertTrue(result.isEnable());
        Assert.assertFalse(result.isDeleted());
        Assert.assertFalse(result.isPrivate());
    }
}
