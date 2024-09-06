package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String DB_USER = "test_user";
    private static final String DB_PASSWORD = "test_user";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static final SessionFactory factory;

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", DB_URL);
            prop.setProperty("hibernate.connection.username", DB_USER);
            prop.setProperty("hibernate.connection.password", DB_PASSWORD);
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

//            prop.setProperty("hibernate.hbm2ddl.auto", "create");

            factory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
