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
public class Service {

    final String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    final String username = "root";
    final String password = "rotflmao0000";
    BasicConnectionPool pool = BasicConnectionPool.create(url, username, password);

    String serviceNo;
    String serviceName;
    Boolean locked;
    String lockedByStaff;
    String unlockTime;

    List<Service> services = new ArrayList<>();

    public Service() {
    }

    public List<Service> listServices() {
        try {
            Connection con = pool.getConnection();
            ResultSet rs = con.prepareStatement("select * from services").executeQuery();
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
        } catch (SQLException s) {
        }
        return services;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockedByStaff() {
        return lockedByStaff;
    }

    public void setLockedByStaff(String lockedByStaff) {
        this.lockedByStaff = lockedByStaff;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }

    @Override
    public String toString() {
        return "Service{" + "serviceNo=" + serviceNo + ", serviceName=" + serviceName + ", locked=" + locked + ", lockedByStaff=" + lockedByStaff + ", unlockTime=" + unlockTime + '}'+'\n';
    }

}
