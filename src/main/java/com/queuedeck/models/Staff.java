/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import com.mysql.cj.xdevapi.Result;
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
public class Staff {
    
    final String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    final String username = "root";
    final String password = "rotflmao0000";
    BasicConnectionPool pool = BasicConnectionPool.create(url, username, password);
    
    private String staffName;
    private String staffNo;
    private Boolean online;
    private int counter;
    private int staffLevel;
    private String staffPassword;
    List<Staff> staffList = new ArrayList<>();

    public Staff() {
    }

    public Staff(String staffName, String staffNo, Boolean online, int counter, int staffLevel, String staffPassword) {
        this.staffName = staffName;
        this.staffNo = staffNo;
        this.online = online;
        this.counter = counter;
        this.staffLevel = staffLevel;
        this.staffPassword = staffPassword;
    }
    
    public List<Staff> getStaffs(){
        int i = 0;
        try{
        Connection con = pool.getConnection();
        ResultSet rs = con.prepareStatement("Select * from staff limit 100").executeQuery();
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
     
    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getStaffLevel() {
        return staffLevel;
    }

    public void setStaffLevel(int staffLevel) {
        this.staffLevel = staffLevel;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    @Override
    public String toString() {
        return "Staff{" + "staffName=" + staffName + ", staffNo=" + staffNo + ", online=" + online + ", counter=" + counter + ", staffLevel=" + staffLevel + ", staffPassword=" + staffPassword + '}'+'\n';
    }
    
}
