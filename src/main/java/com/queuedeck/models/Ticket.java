/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class Ticket {
    
                int ticketNo;
                String tag;
                LocalDateTime timeStamp = LocalDateTime.now();

                public Ticket(int t_no, String tag) {
                    this.ticketNo = t_no;
                    this.tag = tag;
                }
                
                public String changeTimeStampformat(){
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                return "" + timeStamp.format(formatter);     
                        }
                
                public LocalDateTime getTimeStamp() {
                        return timeStamp;
                }

                public void setTimeStamp(LocalDateTime timeStamp) {
                        this.timeStamp = timeStamp;
                }

                public String getTag() {
                        return this.tag;
                }

                public void setTag(String tag) {
                        this.tag = tag;
                }

                public int getTicketNumber() {
                        return this.ticketNo;
                }

                public void setTicketNumber(int ticketNumber) {
                        this.ticketNo = ticketNumber;
                }
                
                public void addTicket(Queue<Ticket> q){
                        q.add(this);
                }

                @Override
                public String toString() {
                        return tag + ticketNo;
                }
}
