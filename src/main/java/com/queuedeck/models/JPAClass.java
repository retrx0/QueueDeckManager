/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import com.queuedeck.controllers.FXMLController;
import com.queuedeck.pool.BasicConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class JPAClass implements DAOInterface{

    @Override
    public List<Service> listServices() {
        List<Service> services = new ArrayList<>();
        try {
            Connection con = FXMLController.pool.getConnection();
            ResultSet rs = con.prepareStatement("select s_no,service,staff_no,locked,unlock_time from services").executeQuery();
            int i = 0;
            while (rs.next()) {
                Service s = new Service();
                s.setServiceNo(rs.getString("s_no"));
                s.setServiceName(rs.getString("service"));
                s.setLockedByStaff(rs.getString("staff_no"));
                s.setLocked(rs.getBoolean("locked"));
                s.setUnlockTime(rs.getString("unlock_time"));
                services.add(i, s);
                i++;
            }
            FXMLController.pool.releaseConnection(con);
        } catch (SQLException s) {}
        return services;
    }
    
    @Override
    public void unlockService(String sno){
        try{
            Connection con = FXMLController.pool.getConnection();
            con.prepareStatement("update services set locked = '0', unlock_time = null where s_no = '" + sno + "'").executeUpdate();
            FXMLController.pool.releaseConnection(con);
        }
        catch(SQLException s){System.out.println(s);}
    }

    @Override
    public List<Staff> listStaff() {
        int i = 0;
        List<Staff> staffList = new ArrayList<>();
        try{
        Connection con = FXMLController.pool.getConnection();
        ResultSet rs = con.prepareStatement("Select staff_name,staff_no,online,counter,staff_level,password from staff limit 100").executeQuery();
        while(rs.next()){
            Staff s = new Staff(rs.getString("staff_name"), rs.getString("staff_no"), rs.getBoolean("online"), rs.getInt("counter"), rs.getInt("staff_level"), rs.getString("password"));
            staffList.add(i, s);
            i++;
            }
            FXMLController.pool.releaseConnection(con);
        }
        catch(SQLException s){System.out.println(s);
        }
        return staffList;
    }
    
    public List<QueueServices> listQueueServices(){
        List<QueueServices> l =new ArrayList<>();
        try {
            Connection con = FXMLController.pool.getConnection();
            ResultSet rs = con.prepareStatement("select q_no,service from queue_services").executeQuery();
            int i = 0;
            while(rs.next()){
                l.add(i, new QueueServices(rs.getString("q_no"), rs.getString("service")));
                i++;
            }
            FXMLController.pool.releaseConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(QueueServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }
    
    @Override
    public List<QueueServices> listQueueServicesWithServiceNO(String s_no){
        List<QueueServices> l =new ArrayList<>();
        try {
            Connection con = FXMLController.pool.getConnection();
            ResultSet rs = con.prepareStatement("select q_no,service from queue_services").executeQuery();
            int i = 0;
            while(rs.next()){
                if(s_no.equals(rs.getString("q_no"))){
                    l.add(i, new QueueServices(rs.getString("q_no"), rs.getString("service")));
                    i++;
                }
            }
            FXMLController.pool.releaseConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(QueueServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }
    
    @Override
    public List<String> getServicesForLevel(String level){
        List<String>  l = new ArrayList<>();
        try{
            Connection con = FXMLController.pool.getConnection();
            ResultSet rs = con.prepareStatement("select level_id,service from staff_level where level_id = '"+level+"'").executeQuery();
            while(rs.next()){
                    l.add(rs.getString("service"));
            }
            FXMLController.pool.releaseConnection(con);
        }catch(SQLException e){
            
        }
        return l;
    }
    
    @Override
    public List<StaffLevel> listAllStaffLevel(){
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
    
    @Override
    public ControlView getControlView(List<ControlView> l, String get){
        ControlView cv = null;
        for(int i=0;i<l.size();i++){
            if(l.get(i).getService().getServiceName().equals(get)){
                cv = l.get(i);
            }
        }
        return cv;
    }
    
    @Override
    public List<ControlView> listControlViewForAllServices(){
        List<ControlView> l = new ArrayList<>();
        DAOInterface d = new JPAClass();
        List<Service> s = d.listServices();
        for (int i = 0; i < s.size(); i++) {
            ControlView cv  = new ControlView(s.get(i));
            l.add(cv);
        }
        return l;
    }
    
}
