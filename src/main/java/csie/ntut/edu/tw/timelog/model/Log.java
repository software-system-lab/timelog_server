package csie.ntut.edu.tw.timelog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;


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
    private Date startTime;
    private Date endTime;

}
