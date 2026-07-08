package ru.itis.shop.infrastructure.persistence.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DriverManagerDataSource implements DataSource {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public DriverManagerDataSource(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection connection = null;

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            makeConnection();
        }

        return connection;
    }

    private void makeConnection() {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new RuntimeException("Not implemented exception");
    }
}
