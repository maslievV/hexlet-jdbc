package hexlet.code;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection conn) {
        connection = conn;
    }

    public void save(User user) throws SQLException{
        if (user.getId() == null) {
            var sql = "INSERT INTO users (username, age) VALUES (?, ?)";
            try (var preparedStmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStmt.setString(1, user.getUsername());
                preparedStmt.setInt(2, user.getAge());
                preparedStmt.executeUpdate();
                var generatedKeys = preparedStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("DB have not returned an id after saving an entity");
                }
            }
        }
    }

    public Optional<User> find(Long id) throws SQLException{
        var sql = "SELECT * FROM users WHERE id = ?";
        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                var username = rs.getString("username");
                var age = rs.getInt("age");
                var user = new User(username, age);
                user.setId(id);
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    public boolean delete(Long id) throws SQLException {
            var sql = "DELETE FROM users WHERE id = ?";
            try (var preparedStmt = connection.prepareStatement(sql)) {
                preparedStmt.setLong(1, id);
                int rowsEffected = preparedStmt.executeUpdate();
                return rowsEffected > 0;
            }
    }

}
