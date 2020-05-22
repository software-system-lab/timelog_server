package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssl.ois.timelog.service.log.*;

@RestController
@RequestMapping("/api/log")
public class LogRestAdapter {
    @Autowired
    LogRecord logRecord;

    @Autowired
    GetLogById getLogById;

    @PostMapping(value = "/record")
    public ResponseEntity<ResponseOutput> newLog(@RequestBody NewLogRequestInput requestBody) {
        RecordInput input = new RecordInput();
        RecordOutput output = new RecordOutput();

        input.setUserID(requestBody.getUserID());
        input.setTitle(requestBody.getTitle());
        input.setStartTime(requestBody.getStartTime());
        input.setEndTime(requestBody.getEndTime());
        input.setDescription(requestBody.getDescription());
        input.setActivityName(requestBody.getActivityName());

        this.logRecord.execute(input, output);

        ResponseOutput responseOutput = new ResponseOutput();
        responseOutput.setLogID(output.getLogID());
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseOutput);
    }

    @PostMapping(value = "/get/id")
    public ResponseEntity<GetByIdOutput> getLogByTitle(@RequestBody GetByIdInput input) {
        GetByIdOutput output = new GetByIdOutput();
        this.getLogById.execute(input, output);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    public static class NewLogRequestInput {
        private String userID;
        private String title;
        private String startTime;
        private String endTime;
        private String description;
        private String activityName;

        public NewLogRequestInput(String userID, String title, String startTime, String endTime, String description) {
            this.userID = userID;
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description;
            this.activityName = "Others";
        }

        public String getUserID() {
            return userID;
        }

        public String getTitle() {
            return title;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public String getDescription() {
            return description;
        }

        public String getActivityName() {
            return activityName;
        }
    }

    private class ResponseOutput {
        
        private String logID;

        public String getLogID() {
            return logID;
        }

        public void setLogID(String logID) {
            this.logID = logID;
        }
    }

}