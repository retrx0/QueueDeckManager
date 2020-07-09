/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class ControlView extends AnchorPane{
    
    final String url = "jdbc:mysql://104.155.33.7:3306/ticketing";
    final String username = "root";
    final String password = "rotflmao0000";
    
    String name;
    Ticket ticket;

    public ControlView() {
        init();
    }

    public ControlView(String name) {
        this.name = name;
        init();
    }
    
    
    
    private void init(){
        
        Button noShow = new Button("No Show");
        Button next = new Button("Next");
        Button transfer = new Button("Transfer");
        Button done = new Button("Done");
        Button callMissed = new Button("Call Missed");
        Button callTrans = new Button("Call Trans");
        Button callAgain = new Button("Call Again");
        ToggleButton lock = new ToggleButton("Lock");
        Button b = new Button("b");
        
        noShow.setPrefSize(100, 50);
        next.setPrefSize(100, 50);
        transfer.setPrefSize(100, 50);
        done.setPrefSize(100, 50);
        callAgain.setPrefSize(100, 50);
        callMissed.setPrefSize(100, 50);
        callTrans.setPrefSize(100, 50);
        lock.setPrefSize(100, 50);
        b.setPrefSize(100, 50);
                
        Label missedLabel = new Label("Missed");
        Label missedCounterLabel = new Label("2");
        Label allLabel = new Label("All Tickets");
        Label transLabel = new Label("Transfered");
        Label transferCounterLabel = new Label("5");
        Label loggedInUser = new Label("");
        Label currentlySerVingLabel = new Label("2");
        Label servingCounterLabel = new Label("serving counter: ");
        Label serviceName = new Label(this.name);
        Label noInlineLabel = new Label("33",new ImageView("/img/icons8-queue-32.png"));
        
        MenuItem logout = new MenuItem("Logout");
        MenuItem changePassword = new MenuItem("Change Password");
        MenuItem ChangeCounter = new MenuItem("Change Counter");
        MenuItem Stats = new MenuItem("Stats");
        MenuItem chkforUpdates = new MenuItem("Chaeck For Updates");
        MenuItem about = new MenuItem("About");
        
        ImageView missedIcon  = new ImageView("/img/170-512.png");
        ImageView transferIcon  = new ImageView("/img/icons8-exchange-802.png");
        ImageView ticketIcon = new ImageView("/img/currentservingticket.png");
        
        missedIcon.setPreserveRatio(true);
        missedIcon.setSmooth(true);
        //missedIcon.setFitHeight(50);
       //missedIcon.setFitWidth(50);
        ticketIcon.setPreserveRatio(true);
        ticketIcon.setSmooth(true);
        ticketIcon.setFitHeight(160);
        ticketIcon.setFitWidth(200);
        ticketIcon.setLayoutX(420);
        ticketIcon.setLayoutY(50);
        
        currentlySerVingLabel.setLayoutX(420);
        currentlySerVingLabel.setLayoutY(50);
        currentlySerVingLabel.setTextAlignment(TextAlignment.CENTER);
        currentlySerVingLabel.setFont(Font.font("sans serif", 50));
        serviceName.setTextAlignment(TextAlignment.CENTER);
        serviceName.setFont(new Font(40));
        serviceName.setTextFill(Paint.valueOf("white"));
        noInlineLabel.setAlignment(Pos.CENTER);
        serviceName.setAlignment(Pos.CENTER);
        allLabel.setAlignment(Pos.CENTER);
        missedLabel.setAlignment(Pos.CENTER);
        transLabel.setAlignment(Pos.CENTER);
        
//        Menu optionsMenu = new Menu("Options",null,changePassword,ChangeCounter,Stats,logout);
//        Menu changeServiceMenu = new Menu("Change Service");
//        Menu helpMenu = new Menu("Help",null,chkforUpdates,about);
//        
//        MenuBar menuBar = new MenuBar(optionsMenu,changeServiceMenu,helpMenu);
        
        CheckBox autoCB= new CheckBox("Auto Call Transfered");
        autoCB.setTextFill(Paint.valueOf("white"));
        autoCB.setAlignment(Pos.CENTER);
        
        ListView lv = new ListView();
        lv.setPrefSize(165, 95);
        lv.setLayoutX(146);
        lv.setLayoutY(102);
        
        HBox hb1 = new HBox(20, noInlineLabel,serviceName,autoCB);
        
        HBox shb1 = new HBox(10, missedLabel,allLabel,transLabel);
        shb1.setAlignment(Pos.CENTER);
        
        VBox svb1 = new VBox(10,missedIcon,missedCounterLabel);
        VBox svb2 = new VBox(10,lv);
        VBox svb3 = new VBox(10,transferIcon,transferCounterLabel);
        svb1.setAlignment(Pos.CENTER);
        svb2.setAlignment(Pos.CENTER);
        svb3.setAlignment(Pos.CENTER); 
        HBox hb2 = new HBox(10, svb1,svb2,svb3);
        
        HBox hb3 = new HBox(10,noShow,next,transfer,done);
        HBox hb4 = new HBox(10, callMissed,callAgain,callTrans,lock);
        VBox vb1 = new VBox(10, hb3,hb4);
        
        VBox VB1 = new VBox(10,noInlineLabel,svb1,noShow,callMissed);
        VB1.setAlignment(Pos.CENTER);
        VBox VB2 = new VBox(10,serviceName,allLabel,lv,next,callAgain);
        VB2.setAlignment(Pos.CENTER);
        VBox VB3 = new VBox(10,new Region(),svb3,transfer,callTrans);
        VB3.setAlignment(Pos.CENTER);
        VBox VB4 = new VBox(10,autoCB,ticketIcon,done,lock);
        VB4.setAlignment(Pos.CENTER);
        HBox HB1 = new HBox(10,VB1,VB2,VB3,VB4);
        HB1.setAlignment(Pos.CENTER);
        HB1.setPadding(new Insets(50, 5, 5, 5));
        HB1.setPrefSize(600, 330);
        
        //HBox hb5 = new HBox(30, loggedInUser, servingCounterLabel);
        
        VBox layoutVBox = new VBox(5,hb1,shb1,hb2,vb1);
        
        hb1.setAlignment(Pos.CENTER);
        hb2.setAlignment(Pos.CENTER);
        hb3.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        vb1.setAlignment(Pos.CENTER);
        layoutVBox.setAlignment(Pos.CENTER);
        layoutVBox.setPadding(new Insets(50, 5, 5, 5));
        layoutVBox.setPrefSize(600, 330);
        
        this.getChildren().add(HB1);
        this.getChildren().addAll(currentlySerVingLabel);
        //layoutVBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("blue"), CornerRadii.EMPTY, Insets.EMPTY)));
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
    }
    
}
