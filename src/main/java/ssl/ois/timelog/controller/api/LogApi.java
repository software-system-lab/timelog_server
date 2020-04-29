package ssl.ois.timelog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssl.ois.timelog.service.log.*;

@RestController
@RequestMapping("/log")
public class LogApi {
    @Autowired
    LogRecord logRecord;

    @PostMapping(value = "/record")
    public ResponseEntity<ResponseOutput> newLog(@RequestBody RecordInput input) {
        RecordOutput output = new RecordOutput();
        ResponseOutput responseOutput = new ResponseOutput();
        this.logRecord.execute(input, output);
        responseOutput.setLogID(output.getLogID());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseOutput);
    }

    @PostMapping(value = "/get/title")
    public ResponseEntity<GetByTitleOutput> getLogByTitle(@RequestBody GetByTitleInput input) {
        GetByTitleOutput output = new GetByTitleOutput();
        this.getLogByTitle.execute(input, output);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    private class ResponseOutput {
        public String getLogID() {
            return logID;
        }

        public void setLogID(String logID) {
            this.logID = logID;
        }

        private String logID;
    }

}
