package ssl.ois.timelog.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogApi {
//    @PostMapping(value = "/record")
//    public ResponseEntity<String> newLog(@RequestBody LogInput log) {
//        LogOutput result = this.logService.newLog(log);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(result);
//    }
}
