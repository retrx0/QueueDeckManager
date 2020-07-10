/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import com.queuedeck.pool.BasicConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class JPAClass implements DAOInterface{
    
    final String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    final String username = "root";
    final String password = "rotflmao0000";
    BasicConnectionPool pool = BasicConnectionPool.create(url, username, password);

    @Override
    public List<Service> listServices() {
        List<Service> services = new ArrayList<>();
        try {
            Connection con = pool.getConnection();
            ResultSet rs = con.prepareStatement("select s_no,service,staff_no,locked,unlock_time from services").executeQuery();
            int i = 0;
            while (rs.next()) {
                Service s = new Service();
                s.setServiceNo(rs.getString("s_no"));
                s.setServiceName(rs.getString("service"));
                s.setLockedByStaff(rs.getString("staff_no"));
                s.setLocked(rs.getBoolean("locked"));
                s.setUnlockTime("unlock_time");
                services.add(i, s);
                i++;
            }
            pool.releaseConnection(con);
        } catch (SQLException s) {}
        return services;
    }

    @Override
    public List<Staff> listStaff() {
        int i = 0;
        List<Staff> staffList = new ArrayList<>();
        try{
        Connection con = pool.getConnection();
        ResultSet rs = con.prepareStatement("Select staff_name,staff_no,online,counter,staff_level,password from staff limit 100").executeQuery();
        while(rs.next()){
            Staff s = new Staff(rs.getString("staff_name"), rs.getString("staff_no"), rs.getBoolean("online"), rs.getInt("counter"), rs.getInt("staff_level"), rs.getString("password"));
            staffList.add(i, s);
            i++;
            }
            pool.releaseConnection(con);
        }
        catch(SQLException s){System.out.println(s);
        }
        return staffList;
    }
    
}
