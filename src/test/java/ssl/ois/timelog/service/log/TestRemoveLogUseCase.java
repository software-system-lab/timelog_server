package ssl.ois.timelog.service.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ssl.ois.timelog.adapter.repository.log.LogRepository;
import ssl.ois.timelog.exception.log.RemoveLogException;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCase;
import ssl.ois.timelog.service.log.remove.RemoveLogUseCaseInput;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRemoveLogUseCase {
    @Autowired
    private RemoveLogUseCase removeLogUseCase;

    @MockBean
    private LogRepository repository;

    @Test
    public void test_full_edit_log() throws RemoveLogException {
        RemoveLogUseCaseInput input = new RemoveLogUseCaseInput();
        input.setId("28f47932-3ad6-44bc-a043-98ce97cf4d11");

        removeLogUseCase.execute(input);

        Mockito.verify(repository).removeLog(Mockito.eq("28f47932-3ad6-44bc-a043-98ce97cf4d11"));
    }
}
