package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.dal.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTools {
    private final static String url = Settings.getPropriete("url");

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url);
        return connection;
    }
}

