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

            var sql2 = "INSERT INTO users (username, age) VALUES (?, ?)";
            try (var preparedStmt = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
                preparedStmt.setString(1, "Vlad");
                preparedStmt.setInt(2, 19);
                preparedStmt.executeUpdate();

                preparedStmt.setString(1, "Vika");
                preparedStmt.setInt(2, 19);
                preparedStmt.executeUpdate();
            }

            var sql3 = "SELECT * FROM users";
            try (var stmt3 = conn.createStatement()) {
                var rs = stmt3.executeQuery(sql3);
                while (rs.next()) {
                    System.out.printf("Name: %s, age: %d%n", rs.getString("username"), rs.getInt("age"));
                }
            }
        }

    }
}