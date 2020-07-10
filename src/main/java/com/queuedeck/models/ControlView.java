/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import com.queuedeck.controllers.FXMLController;
import java.util.prefs.Preferences;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
        Label allLabel = new Label("All");
        Label transLabel = new Label("Transfered");
        Label transferCounterLabel = new Label("5");
        Label currentlySerVingLabel = new Label("2");
        Label serviceName = new Label(this.name);
        Label noInlineLabel = new Label("33",new ImageView("/img/icons8-queue-32.png"));
        
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
        ticketIcon.setLayoutX(390);
        ticketIcon.setLayoutY(70);
        
        currentlySerVingLabel.setLayoutX(455);
        currentlySerVingLabel.setLayoutY(85);
        currentlySerVingLabel.setTextAlignment(TextAlignment.CENTER);
        currentlySerVingLabel.setFont(Font.font(50));
        currentlySerVingLabel.setId("tlable");
        
        serviceName.setTextAlignment(TextAlignment.CENTER);
        serviceName.setFont(new Font(40));
        serviceName.setTextFill(Paint.valueOf("white"));
        serviceName.setId("title");
        
        noInlineLabel.setAlignment(Pos.CENTER);
        serviceName.setAlignment(Pos.CENTER);
        allLabel.setAlignment(Pos.CENTER);
        missedLabel.setAlignment(Pos.CENTER);
        transLabel.setAlignment(Pos.CENTER);
        
        CheckBox autoCB= new CheckBox("Auto Call Transfered");
        autoCB.setTextFill(Paint.valueOf("white"));
        autoCB.setAlignment(Pos.CENTER);
        
        ListView lv = new ListView();
        lv.setPrefSize(165, 95);
        lv.setLayoutX(146);
        lv.setLayoutY(102);
        Region r = new Region();
        r.setPrefSize(130, 50);
        HBox hb1 = new HBox(30, noInlineLabel,r,serviceName,autoCB);
        
        HBox shb1 = new HBox(30, missedLabel,allLabel,transLabel);
        shb1.setAlignment(Pos.CENTER_LEFT);
        shb1.setPadding(new Insets(0, 0, 0, 50));
        
        VBox svb1 = new VBox(10,missedIcon,missedCounterLabel);
        VBox svb2 = new VBox(10,lv);
        VBox svb3 = new VBox(10,transferIcon,transferCounterLabel);
        svb1.setAlignment(Pos.CENTER);
        svb2.setAlignment(Pos.CENTER);
        svb3.setAlignment(Pos.CENTER); 
        Region r3 = new Region();
        r3.setPrefWidth(20);
        HBox hb2 = new HBox(15, r3,svb1,svb2,svb3);
        hb2.setPadding(new Insets(0, 0, 0, 30));
        //hb2.setBackground(new Background(new BackgroundFill(Paint.valueOf("blue"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Region r2= new Region();
        r2.setPrefSize(50, 50);
        HBox hb3 = new HBox(15,noShow,next,transfer,done);
        HBox hb4 = new HBox(15, callMissed,callAgain,callTrans,lock);
        VBox vb1 = new VBox(15, hb3,hb4);
        vb1.setPadding(new Insets(20, 0, 0,0));
        //HBox hb5 = new HBox(30, loggedInUser, servingCounterLabel);
        
        VBox layoutVBox = new VBox(10,hb1,shb1,hb2,vb1);
        
        hb1.setAlignment(Pos.CENTER);
        //hb2.setAlignment(Pos.CENTER_LEFT);
        hb3.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        vb1.setAlignment(Pos.CENTER);
        layoutVBox.setAlignment(Pos.CENTER);
        layoutVBox.setPadding(new Insets(30, 5, 5, 5));
        layoutVBox.setPrefSize(600, 360);
        
        this.getChildren().add(layoutVBox);
        this.getChildren().addAll(ticketIcon,currentlySerVingLabel);
        //layoutVBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("blue"), CornerRadii.EMPTY, Insets.EMPTY)));
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        
        //OnActions
        FXMLController f = new FXMLController();
        lock.setOnAction((t) -> {
            Preferences prefs = Preferences.userNodeForPackage(getClass());
            System.out.println((prefs.get("Staff No", "")));
            //f.lockButtonPerformAction(lock, "A");
        });
        
        next.setOnAction((t) -> {
           //f.nextButtonActionToPerform(lv, autoCB, currentlySerVingLabel, "A");
        });
        
    }
    
}
