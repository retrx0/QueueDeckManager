/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class BasicConnectionPool implements ConnectionPool{
    
    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 20;
     
    public static BasicConnectionPool create(String url, String user,String password) {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                pool.add(createConnection(url, user, password));
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not connect to database");
                alert.setContentText("There was a problem connecting to database server, please check that you have active internet connectivity and try again. If problem persist contact support@queuedeck.com");
                alert.showAndWait();
                Logger.getLogger(BasicConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            }
        }
        return new BasicConnectionPool(url, user, password, pool);
    }

    public BasicConnectionPool(String url, String user, String password, List<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }
    
    public static HikariDataSource hikariTest(String url,String user,String password){
        HikariConfig jdbcConfig = new HikariConfig();
        jdbcConfig.setPoolName("hpool");
        jdbcConfig.setMaximumPoolSize(30);
        jdbcConfig.setMinimumIdle(2);
        jdbcConfig.setJdbcUrl(url);
        jdbcConfig.setUsername(user);
        jdbcConfig.setPassword(password);
        jdbcConfig.addDataSourceProperty("cachePrepStmts", "true");
        jdbcConfig.addDataSourceProperty("prepStmtCacheSize", "256");
        jdbcConfig.addDataSourceProperty("prepStmtCacheSqlLimit","2048" );
        jdbcConfig.addDataSourceProperty("useServerPrepStmts", "true");
        return new HikariDataSource(jdbcConfig);
    }
    
    public static Connection createConnection(String url, String user, String password) throws SQLException{
//            return HikariCPDataSource.getConnection();
            return DriverManager.getConnection(url,user,password);
    }
    
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void shutdown() throws SQLException {
    usedConnections.forEach(this::releaseConnection);
    for (Connection c : connectionPool) {
        c.close();
    }
    connectionPool.clear();
}

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionPool.remove(connectionPool.size() - 1);
            usedConnections.add(connection);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not connect to database");
            alert.setContentText("There was a problem connecting to database server");
            alert.showAndWait();
        }
        return connection;
    }
    
   /* public void Connection addConnection(String url, String user,String password){
        try{connectionPool.add(createConnection(url, user, password));}catch(SQLException se){}
    }*/
}
    


