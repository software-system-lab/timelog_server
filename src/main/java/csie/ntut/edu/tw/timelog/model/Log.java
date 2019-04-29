package csie.ntut.edu.tw.timelog.model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Log")
public class Log {
    @Id
    private String logID;
    private String userID;
    private String tag;
    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;

}
