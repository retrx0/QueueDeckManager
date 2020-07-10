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
    public List<Staff> listStaff();
    
}
