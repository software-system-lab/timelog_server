package ssl.ois.timelog.adapter.database;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDriverAdapter {
    @Value("${mysql.host}")
    private static String host;
    @Value("${mysql.timelog_db")
    private static String database;
    @Value("${mysql.username}")
    private static String user;
    @Value("${mysql.password")
    private static String password;

    public final static Connection getConnection() throws SQLException {
        final String url = host + "/" + database;
        return DriverManager.getConnection(url, user, password);
    }

    public final static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
