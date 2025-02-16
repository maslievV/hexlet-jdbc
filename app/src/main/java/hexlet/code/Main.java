package hexlet.code;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

        var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), age INT)";
        var statement = conn.createStatement();
        statement.execute(sql);
        statement.close();

        var sql2 = "INSERT INTO users (name, age) VALUES ('Vlad', 19)";
        var statement2 = conn.createStatement();
        statement2.executeUpdate(sql2);
        statement2.close();

        var sql3 = "SELECT * FROM users";
        var statement3 = conn.createStatement();
        var resultSet = statement3.executeQuery(sql3);
        while (resultSet.next()) {
            System.out.println("Name: " + resultSet.getString("name"));
            System.out.println("Age: " + resultSet.getInt("age"));
        }

    }
}