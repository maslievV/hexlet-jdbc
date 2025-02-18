package hexlet.code;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {

        try (var conn = DriverManager.getConnection("jdbc:h2:mem:my_database")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), age INT)";
            try (var stmt = conn.createStatement()) {
                stmt.execute(sql);
            }

            var dao = new UserDAO(conn);
            var user = new User("John", 22);
            System.out.println(user.getId());
            dao.save(user);
            System.out.println(user.getId());

            var user2 = new User("Lock", 90);
            dao.save(user2);
            dao.delete(2L);
            System.out.println(dao.find(2L).toString());
        }
    }
}