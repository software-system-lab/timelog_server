package csie.ntut.edu.tw.timelog.repository;

import java.util.List;
import csie.ntut.edu.tw.timelog.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {
    public List<Log> findByUserID(String userID);
}
