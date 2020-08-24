/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import javafx.scene.control.ListView;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public interface SQLQueries {
    
    public void lockTicket(String staffNo, String tag);
    public void addTimeDone(String staffNo, String tag,ListView allListVIew);
    public int getNumberInline(Service sno);
    public int getNumberTransfered(Service no);
}
