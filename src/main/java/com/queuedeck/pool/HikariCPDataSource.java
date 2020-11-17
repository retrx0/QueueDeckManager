/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.pool;

import com.queuedeck.controllers.FXMLController;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class HikariCPDataSource {
    
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;
    
    static {
        config.setMaximumPoolSize(30);
        config.setMinimumIdle(2);
        config.setJdbcUrl(FXMLController.URL_STRING);
        config.setUsername(FXMLController.USERNAME_STRING);
        config.setPassword(FXMLController.PASSWORD_STRING);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }
    
    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(HikariCPDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private HikariCPDataSource(){}
    
}
