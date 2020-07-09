/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public interface SQLQueries {
    
    public void lockTicket(String staffNo, String tag);
    public void getTicket(String staffNo, String tag);
    
}
