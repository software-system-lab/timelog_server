package ssl.ois.timelog.adapter.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v2/activity")
public class ActivityRestAdapter {
    @PostMapping("/add")
    public void addActivity(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @GetMapping("/list")
    public void listActivity(HttpServletResponse response) {

    }

    @PutMapping("/edit/{activityId}")
    public void editActivity(@PathVariable String activityId, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @DeleteMapping("/remove/{activityId}")
    public void removeActivity(@PathVariable String activityId, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
