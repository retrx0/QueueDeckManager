/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import java.util.ArrayList;
import java.util.List;
import com.queuedeck.controllers.FXMLController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class QueueServices {
    
    private String q_no;
    private String queueServiceName;

    public QueueServices(String q_no, String service) {
        this.q_no = q_no;
        this.queueServiceName = service;
    }

    public QueueServices() {
    }

    public String getQ_no() {
        return q_no;
    }

    public void setQ_no(String q_no) {
        this.q_no = q_no;
    }

    public String getQueueServiceName() {
        return queueServiceName;
    }

    public void setQueueServiceName(String queueServiceName) {
        this.queueServiceName = queueServiceName;
    }
    
    public List<QueueServices> listQueueServices(String s_no){
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
    public String toString() {
        return "QueueServices{" + "q_no=" + q_no + ", service=" + queueServiceName + '}';
    }
    
}
