package ssl.ois.timelog.adapter.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssl.ois.timelog.adapter.database.MysqlDriverAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
public class HealthCheck {

    @Autowired
    MysqlDriverAdapter adapter;

    @GetMapping(value = "/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Timelog server is healthy");
    }

    @GetMapping(value = "/test")
    public String foo() {
        try {
            Connection c = adapter.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT * from activity_user_mapper LIMIT 1");
            ResultSet result = statement.executeQuery();

            int i = 0;
            result.next();
            System.out.println(result.getString("activity_type_name"));
//            while (result.next()) {
//                i++;
//                System.out.println(result.getString("activity_type_name"));
//                if (i > 20) break;
//            }
            c.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return "get successfully";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }
}
