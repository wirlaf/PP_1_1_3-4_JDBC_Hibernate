package jm.task.core.jdbc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.model.User;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    static Connection connection = getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = """
                create table USERS
                (
                ID bigint not null auto_increment,
                NAME varchar(255) NOT NULL,
                LASTNAME varchar(255) NOT NULL,
                AGE BIGINT NOT NULL,
                CONSTRAINT USER_PKEY PRIMARY KEY (ID)
                )""";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("USE test_db;");
            statement.executeUpdate(sql);
        } catch (SQLSyntaxErrorException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE `test_db`.`users`";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLSyntaxErrorException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.execute("USE test_db;");
            ps.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.execute("USE test_db;");
            ps.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM test_db.users";
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                users.add(user);
            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("USE test_db;");
            statement.executeUpdate(sql);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }
}
