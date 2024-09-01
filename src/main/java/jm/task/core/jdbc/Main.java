package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte) 18);
        userService.saveUser("Petr", "Ivanov", (byte) 24);
        userService.saveUser("Oleg", "Smirnov", (byte) 22);
        userService.saveUser("Andrey", "Sidorov", (byte) 20);
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

