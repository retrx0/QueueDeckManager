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

}
