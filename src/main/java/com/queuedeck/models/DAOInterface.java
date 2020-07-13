/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import java.util.List;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public interface DAOInterface {
    public List<Service> listServices();
    public void unlockService(String sno);
    public List<Staff> listStaff();
    public List<QueueServices> listQueueServices();
    public List<QueueServices> listQueueServicesWithServiceNO(String s_no);
    public List<String> getServicesForLevel(String level);
    public List<StaffLevel> listAllStaffLevel();
    public ControlView getControlView(List<ControlView> l, String get);
    public List<ControlView> listControlViewForAllServices();
}
