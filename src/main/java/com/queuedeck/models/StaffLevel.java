/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import com.queuedeck.controllers.FXMLController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class StaffLevel {
    
    int level_ID;
    String levelService;

    public StaffLevel() {
    }

    public StaffLevel(int level_ID, String levelService) {
        this.level_ID = level_ID;
        this.levelService = levelService;
    }
    
    public List<String> getServicesForLevel(String level){
        List<String>  l = new ArrayList<>();
        try{
            Connection con = FXMLController.pool.getConnection();
            ResultSet rs = con.prepareStatement("select level_id,service from staff_level where level_id = '"+level+"' ").executeQuery();
            while(rs.next()){
                    l.add(rs.getString("service"));
            }
            FXMLController.pool.releaseConnection(con);
        }catch(SQLException e){
            
        }
        return l;
    }
    
    public List<StaffLevel> listAllServices(){
        List<StaffLevel>  l = new ArrayList<>();
        try{
            Connection con = FXMLController.pool.getConnection();
            ResultSet rs = con.prepareStatement("select level_id,service from staff_level").executeQuery();
            while(rs.next()){
                StaffLevel sl = new StaffLevel(rs.getInt("level_id"), rs.getString("service"));
                l.add(sl);
            }
            FXMLController.pool.releaseConnection(con);
        }catch(SQLException e){
            
        }
        return l;
    }

    public int getLevel_ID() {
        return level_ID;
    }

    public void setLevel_ID(int level_ID) {
        this.level_ID = level_ID;
    }

    public String getLevelService() {
        return levelService;
    }

    public void setLevelService(String levelService) {
        this.levelService = levelService;
    }
    
    
    
}
