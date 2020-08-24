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
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListView;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class JPASQLQueries implements SQLQueries {

    LocalDate local_date = LocalDate.now();

    public JPASQLQueries() {
    }
    
    @Override
    public void lockTicket(String staffNo, String tag) {
        Connection con = FXMLController.pool.getConnection();
        try {
            Statement stmt = con.createStatement();
            //first lock the Ticket
            stmt.executeUpdate("update tickets set locked = true, staff_no = '" + staffNo + "' where time_called is null and locked is null and missing_client is null and tag='" + tag + "' and t_date='" + local_date + "' limit 1");
            stmt.executeUpdate("update transfer set locked = true, staff_no = '" + staffNo + "' where time_called is null and locked is null and trans_to='" + tag + "' and t_date='" + local_date + "' limit 1");
        } catch (SQLException ex) {
            Logger.getLogger(JPASQLQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLController.pool.releaseConnection(con);
    }

    @Override
    public void addTimeDone(String staffNo, String tag, ListView allListVIew) {
        Connection con = FXMLController.pool.getConnection();
        try {
            Statement stmt = con.createStatement();
            String localtime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
            if (!allListVIew.getItems().isEmpty()) {
                if (allListVIew.getItems().size() > 2) {
                    allListVIew.getItems().remove(0);
                }
                String vtkt = allListVIew.getItems().get(allListVIew.getItems().size() - 1).toString();
                String vtag = vtkt.substring(0, 1);
                int vtn = Integer.valueOf(vtkt.substring(1, vtkt.indexOf(" ")));
                if (vtag.equals(tag)) {
                    stmt.executeUpdate("update tickets set time_done = '" + localtime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and time_done is null and t_date='" + local_date + "'");
                } else {
                    stmt.executeUpdate("update transfer set time_done = '" + localtime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and time_done is null and t_date='" + local_date + "'");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JPASQLQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLController.pool.releaseConnection(con);
    }

    @Override
    public int[] getNumberInline(Service sno) {
        int i= 0;
        int[] arr = new int[2];
        try {
            Connection con = FXMLController.pool.getConnection();
//            ResultSet rs5 = con.prepareStatement("select sum(case when time_called IS NULL and tag = '" + sno.getServiceNo() + "' then 1 else 0 end) as count_num from tickets where t_date='" + local_date + "'").executeQuery();
            ResultSet rs5 = con.prepareStatement("select count(if(time_called is NULL and tag = '"+sno.getServiceNo()+"', 1, NULL)) 'count_num' from tickets where t_date = '"
                    +local_date+"' union select count(if(time_called is NULL and trans_to = '"+sno.getServiceNo()+"', 1, NULL)) 'count_num_trans' from transfer where t_date = '"+local_date+"'").executeQuery();    
            while (rs5.next()) {
                arr[i] = rs5.getInt("count_num");
                i++;
            }
            FXMLController.pool.releaseConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(JPASQLQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    @Override
    public int getNumberTransfered(Service no) {
        int pplineb = 0;
        try {
            Connection con = FXMLController.pool.getConnection();
            ResultSet rs5b = con.prepareStatement("select count(if(time_called is NULL and trans_to = '"+no.getServiceNo()+"', 1, NULL)) 'count_num' from transfer where t_date = '"+local_date+"'").executeQuery();
            while (rs5b.next()) {
                pplineb = rs5b.getInt("count_num");
            }
            FXMLController.pool.releaseConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(JPASQLQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pplineb;
    }

}
