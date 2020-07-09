/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import com.queuedeck.pool.BasicConnectionPool;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class JPASQLQueries {
    final String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    final String username = "root";
    final String password = "rotflmao0000";
    BasicConnectionPool pool = BasicConnectionPool.create(url, username, password);
    
    
}
