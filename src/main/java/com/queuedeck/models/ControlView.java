/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import com.queuedeck.controllers.FXMLController;
import static com.queuedeck.controllers.FXMLController.pool;
import com.queuedeck.effects.FlashTransition;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.util.Pair;


/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class ControlView extends AnchorPane{
    
    String name;
    Ticket ticket;
    Service service;
    
    String local_date = LocalDate.now().toString();

    public ControlView() {
        init();
    }
    
    public ControlView(Service s) {
        this.service = s;
        init();
        serviceName.setText(s.getServiceName());
    }

    public ControlView(String name) {
        this.name = name;
        //this.service = service;
        init();
    }

    public Service getService() {
        return service;
    }
    
    
    String tkt;
    DAOInterface d = new JPAClass();
    
    JFXButton noShow = new JFXButton("No Show");
    JFXButton next = new JFXButton("Next");
    JFXButton transfer = new JFXButton("Transfer");
    JFXButton done = new JFXButton("Done");
    JFXButton callMissed = new JFXButton("Call Missed");
    JFXButton callTrans = new JFXButton("Call Trans");
    JFXButton callAgain = new JFXButton("Call Again");
    public JFXToggleButton lock = new JFXToggleButton();
    
    Label missedLabel = new Label("Missed");
    public Label missedCounterLabel = new Label("0");
    Label allLabel = new Label("All");
    Label transLabel = new Label("Transfered");
    public Label transferCounterLabel = new Label("0");
    Label currentlySerVingLabel = new Label("");
    Label serviceName = new Label();
    public Label noInlineLabel = new Label("",new ImageView("/img/icons8-queue-32.png"));
    
    ImageView missedIcon  = new ImageView("/img/170-512.png");
    ImageView transferIcon  = new ImageView("/img/icons8-exchange-802.png");
    ImageView ticketIcon = new ImageView("/img/currentservingticket.png");

    JFXCheckBox autoCb = new JFXCheckBox("Auto Call Transfered");
    
    public ListView lv = new ListView();
     
    private void init(){
        
        noShow.setPrefSize(100, 50);
        next.setPrefSize(100, 50);
        transfer.setPrefSize(100, 50);
        done.setPrefSize(100, 50);
        callAgain.setPrefSize(100, 50);
        callMissed.setPrefSize(100, 50);
        callTrans.setPrefSize(100, 50);
        lock.setPrefSize(100, 30);
        
        missedIcon.setPreserveRatio(true);
        missedIcon.setSmooth(true);
        missedIcon.setFitHeight(50);
        missedIcon.setFitWidth(50);
        transferIcon.setFitHeight(50);
        transferIcon.setFitWidth(50);
        transferIcon.setPreserveRatio(true);
        
        ticketIcon.setPreserveRatio(true);
        ticketIcon.setSmooth(true);
        ticketIcon.setFitHeight(140);
        ticketIcon.setFitWidth(180);
        ticketIcon.setLayoutX(390);
        ticketIcon.setLayoutY(70);
        ticketIcon.relocate(415, 55);
        ticketIcon.setId("ticket-icon");
        
        currentlySerVingLabel.setAlignment(Pos.CENTER);
        currentlySerVingLabel.relocate(415, 60);
        currentlySerVingLabel.setPrefSize(180, 140);
        currentlySerVingLabel.setTextAlignment(TextAlignment.CENTER);
        currentlySerVingLabel.setFont(Font.font(50));
        currentlySerVingLabel.setId("cslabel");
        
        serviceName.setTextAlignment(TextAlignment.CENTER);
        serviceName.setAlignment(Pos.CENTER);
        serviceName.setText(name);
        serviceName.setFont(new Font(25));
        serviceName.setTextFill(Paint.valueOf("white"));
        serviceName.setId("title");
        
        noInlineLabel.setAlignment(Pos.CENTER);
        noInlineLabel.setFont(new Font(20));
        noInlineLabel.setTextFill(Paint.valueOf("white"));
        serviceName.setAlignment(Pos.CENTER);
        allLabel.setTextAlignment(TextAlignment.CENTER);
        missedLabel.setTextAlignment(TextAlignment.CENTER);
        transLabel.setTextAlignment(TextAlignment.CENTER);
                
        lv.setPrefSize(165, 95);
        lv.setLayoutX(146);
        lv.setLayoutY(102);
        
        lock.setText("Lock");
        lock.setTextFill(Paint.valueOf("white"));
        lock.setWrapText(true);
        
        noInlineLabel.relocate(10, 30);
        serviceName.relocate(150, 30);
        
        autoCb.relocate(430, 360);
        autoCb.setTextFill(Paint.valueOf("white"));
        autoCb.setSelected(true);
        
        VBox svb1 = new VBox(10,missedLabel,missedIcon,missedCounterLabel);
        VBox svb2 = new VBox(10,lv);
        VBox svb3 = new VBox(10,transLabel,transferIcon,transferCounterLabel);
        svb1.setAlignment(Pos.CENTER);
        svb2.setAlignment(Pos.CENTER);
        svb3.setAlignment(Pos.CENTER); 
        Region r3 = new Region();
        r3.setPrefWidth(20);
        HBox hb2 = new HBox(20, r3,svb1,svb2,svb3);
        hb2.setPadding(new Insets(0, 0, 0, 40));
        
        Region r2= new Region();
        r2.setPrefSize(50, 50);
        HBox hb3 = new HBox(15,noShow,next,transfer,done);
        HBox hb4 = new HBox(15, callMissed,callAgain,callTrans,lock);
        VBox vb1 = new VBox(15, hb3,hb4);
        vb1.setPadding(new Insets(20, 0, 0,0));
        
        VBox layoutVBox = new VBox(15,hb2,vb1);
        
        hb3.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        vb1.setAlignment(Pos.CENTER);
        layoutVBox.setAlignment(Pos.CENTER);
        layoutVBox.setPadding(new Insets(60, 5, 5, 5));
        layoutVBox.setPrefSize(600, 360);
        
        this.getChildren().addAll(noInlineLabel, serviceName,autoCb,layoutVBox,ticketIcon,currentlySerVingLabel);
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("control-view-pane");
        
        //OnActions
        done.setOnAction((t) -> {
            doneButtonPerformAction(lv, this.service.getServiceNo());
        });
        lock.setOnAction((t) -> {
            lockButtonPerformAction(lock, this.service.getServiceNo());
        });
        
        next.setOnAction((t) -> {
           nextButtonActionToPerform(lv, autoCb, currentlySerVingLabel, this.service.getServiceNo());
        });
        
        noShow.setOnAction((t) -> {
            noShowActionToPerform(lv, missedCounterLabel, this.service.getServiceNo());
        });
        
        transfer.setOnAction((t) -> {
            transferButtonActionToPerform(lv);
        });
        
        callAgain.setOnAction((t) -> {
            callAgainActionToPerform(lv, currentlySerVingLabel);
        });
        
        callMissed.setOnAction((t) -> {
            callMissedActionToPerform(lv, currentlySerVingLabel, missedCounterLabel);
        });
        
        callTrans.setOnAction((t) -> {
            callTransferedActionToPerform(lv, currentlySerVingLabel, this.service.getServiceNo());
        });
    }
    
    void flash(Node l) {
        FlashTransition ft = new FlashTransition(l);
        ft.play();
    }
    
    public void callTicketToDisplay(String tkt) {
        System.out.println(tkt+" goto counter "+FXMLController.loggedInStaff.getCounter());
    }
    
    void createAlert(Alert.AlertType alertType, String title, String contextText, String headerText) {
        JFXAlert al  = new JFXAlert();
        al.initModality(Modality.APPLICATION_MODAL);
        al.setOverlayClose(false);
        al.setAnimation(JFXAlertAnimation.NO_ANIMATION);
        
        Label ct = new Label(contextText);
        ct.setStyle("-fx-text-fill: #2E315B");
        Label h = new Label(headerText);
        h.setStyle("-fx-text-fill: #2E315B");
        
        VBox vb= new VBox(15, h,ct);
        
        JFXButton doneBtn =  new JFXButton("OK");
        doneBtn.setDefaultButton(true);
        doneBtn.setPrefSize(50, 25);
        
        JFXDialogLayout lay = new JFXDialogLayout();
        lay.setHeading(h);
        lay.setBody(ct);
        lay.setActions(doneBtn);
        
        al.setTitle(title);
        
        JFXDialog di =new JFXDialog((StackPane)this.getParent(), lay, JFXDialog.DialogTransition.LEFT);
        doneBtn.setOnAction((t) -> {
           di.close();
        });
        di.show();
    }
    
    public void nextButtonActionToPerform(ListView allListVIew, CheckBox autoTransferCB, Label currentlyServingLabel, String tag) {
        try {
            Connection con = pool.getConnection();
            Statement stmt = con.createStatement();
            //first lock the Ticket
            stmt.executeUpdate("update tickets set locked = true, staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where time_called is null and locked is null and missing_client is null and tag='" + tag + "' and t_date='" + local_date + "' limit 1");
            stmt.executeUpdate("update transfer set locked = true, staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where time_called is null and locked is null and trans_to='" + tag + "' and t_date='" + local_date + "' limit 1");
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
            ResultSet rs = con.prepareStatement("select tag,t_no,service from tickets where time_called is null and locked is not null and missing_client is null and tag='" + tag + "' and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and t_date='" + local_date + "' limit 1").executeQuery();
            ResultSet rs2 = con.prepareStatement("select tag, t_no from transfer where time_called is null and locked is not null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and trans_to = '" + tag + "' and t_date='" + local_date + "' limit 1").executeQuery();
            if (rs.next()) {
                if (autoTransferCB.isSelected()) {
                    if (rs2.next()) {
                        String vtag = rs2.getString("tag");
                        int vt_no = rs2.getInt("t_no");
                        tkt = vtag + vt_no;
                        ResultSet gs = con.prepareStatement("select service from transfer where tag = '" + vtag + "' and t_no = '" + vt_no + "' and t_date='" + local_date + "'").executeQuery();
                        String service1 = "";
                        while (gs.next()) {
                            service1 = gs.getString("service");
                        }
                        stmt.executeUpdate("update transfer set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where tag = '" + vtag + "' and t_no = '" + vt_no + "' and time_called is null and t_date='" + local_date + "'");
                        allListVIew.getItems().add(new Ticket(vt_no, vtag) + " - " + service1);
                        currentlyServingLabel.setText(tkt);
                        flash(currentlyServingLabel);
                        callTicketToDisplay(tkt);
                        stmt.executeUpdate("update tickets set locked = null, staff_no = null where time_called is null and locked is not null and missing_client is null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and tag='" + tag + "' and t_date='" + local_date + "' limit 1");
                    } else {
                        String ntag = rs.getString("tag");
                        int ntn = rs.getInt("t_no");
                        String service2 = rs.getString("service");
                        String ntkt = ntag + ntn;
                        stmt.executeUpdate("update tickets set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "', alert = '1'  where t_no = '" + ntn + "' and tag = '" + ntag + "' and time_called is null and t_date='" + local_date + "'");
                        allListVIew.getItems().add(new Ticket(ntn, ntag) + " - " + service2);
                        currentlyServingLabel.setText(ntkt);
                        flash(currentlyServingLabel);
                        callTicketToDisplay(ntkt);
                        stmt.executeUpdate("update transfer set locked = null, staff_no = null where time_called is null and locked is not null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and trans_to='" + tag + "' and t_date='" + local_date + "' limit 1");
                    }
                } else if (!autoTransferCB.isSelected()) {
                    String ntag = rs.getString("tag");
                    int ntn = rs.getInt("t_no");
                    String service3 = rs.getString("service");
                    String ntkt = ntag + ntn;
                    stmt.executeUpdate("update tickets set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' , alert = '1' where t_no = '" + ntn + "' and tag = '" + ntag + "' and time_called is null and t_date='" + local_date + "'");
                    allListVIew.getItems().add(new Ticket(ntn, ntag) + " - " + service3);
                    currentlyServingLabel.setText(ntkt);
                    flash(currentlyServingLabel);
                    callTicketToDisplay(ntkt);
                    stmt.executeUpdate("update transfer set locked = null, staff_no = null where time_called is null and locked is not null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and trans_to='" + tag + "' and t_date='" + local_date + "' limit 1");
                }
            } else if (rs2.next()) {
                if (autoTransferCB.isSelected()) {
                    String vtag = rs2.getString("tag");
                    int vt_no = rs2.getInt("t_no");
                    tkt = vtag + vt_no;
                    ResultSet gs = con.prepareStatement("select service from transfer where tag = '" + vtag + "' and t_no = '" + vt_no + "'and t_date='" + local_date + "'").executeQuery();
                    String service4 = "";
                    while (gs.next()) {
                        service4 = gs.getString("service");
                    }
                    stmt.executeUpdate("update transfer set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where tag = '" + vtag + "' and t_no = '" + vt_no + "' and time_called is null and t_date='" + local_date + "'");
                    allListVIew.getItems().add(new Ticket(vt_no, vtag) + " - " + service4);
                    currentlyServingLabel.setText(tkt);
                    flash(currentlyServingLabel);
                    callTicketToDisplay(tkt);
                    stmt.executeUpdate("update tickets set locked = null, staff_no = null where time_called is null and locked is not null and missing_client is null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and tag='" + tag + "' and t_date='" + local_date + "' limit 1");
                } else {
                    createAlert(Alert.AlertType.WARNING, "Empty Queue", "Check auto call transfer box to call transfered ticket automatically", "No more ticket on queue");
                }
            } else {
                createAlert(Alert.AlertType.WARNING, "Empty Queue", "Queue is empty", "Empty Queue");
            }
            pool.releaseConnection(con);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void noShowActionToPerform(ListView allListView, Label missedNoLabel, String tag) {
        if (!allListView.getItems().isEmpty()) {
            String v = allListView.getItems().get(allListView.getItems().size() - 1).toString();
            if (v != null) {
                String vtag = v.substring(0, 1);
                String vtn = v.substring(1, v.indexOf(" "));
                String time_done = "";
                String missing_client = "";
                String currentTime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
                try {
                    Connection con = pool.getConnection();
                    PreparedStatement stmt = con.prepareStatement("select time_done, missing_client from tickets where tag = '" + vtag + "' and t_no = '" + vtn + "' and t_date='" + local_date + "'");
                    ResultSet rs = stmt.executeQuery();
                    Statement stmt2 = con.createStatement();
                    if (rs.next()) {
                        time_done = rs.getString("time_done");
                        missing_client = rs.getString("missing_client");
                        if (time_done == null && missing_client == null) {
                            stmt2.executeUpdate("update tickets set missing_client = '" + currentTime + "', time_called = Null where tag = '" + vtag + "' and t_no = '" + vtn + "' and t_date='" + local_date + "' ");
                            allListView.getItems().remove(allListView.getItems().size() - 1);
                            PreparedStatement count = con.prepareStatement("select count(*) from tickets where missing_client is not null and time_called is null and tag = '" + tag + "' and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and t_date='" + local_date + "'");
                            ResultSet counter = count.executeQuery();
                            while (counter.next()) {
                                String value = counter.getString("count(*)");
                                missedNoLabel.setText(value);
                            }
                        } else {
                            createAlert(Alert.AlertType.WARNING, "Error", "Ticket cannot be added to missed queue", "Ticket Error");
                        }
                    }
                    pool.releaseConnection(con);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                createAlert(Alert.AlertType.WARNING, "Empty queue", "Queue is empty", "Empty queue");
            }
        } else {
            createAlert(Alert.AlertType.WARNING, "Error calling ticket", "No ticket called", "Ticket Error");
        }
    }
    
    Optional<Pair<String, String>> transferChoiseDialogWithServices() {
        Label h  = new Label("Transfer Ticket");
        h.setStyle("-fx-text-fill: #2E315B");
        
        JFXComboBox queueSelection = new JFXComboBox();
        JFXComboBox servicesSelction = new JFXComboBox();
        queueSelection.setLabelFloat(true);servicesSelction.setLabelFloat(true);

        try {
            Connection con = pool.getConnection();
            List<String> ql = new ArrayList<>();
            ResultSet gq = con.prepareStatement("select service from services where service != '" + this.service.getServiceName() + "'").executeQuery();
            queueSelection.getItems().clear();
            while (gq.next()) {
                String temp = gq.getString("service");
                queueSelection.getItems().addAll(temp);
                ql.add(temp);
            }
            queueSelection.setPromptText("Select Queue");
            servicesSelction.setPromptText("Select Service");
            queueSelection.setOnAction((t) -> {
                for (int i = 0; i < ql.size(); i++) {
                    if (queueSelection.getSelectionModel().getSelectedItem().toString().equals(ql.get(i))) {
                        try {
                            servicesSelction.getItems().clear();
                            ResultSet gs = con.prepareStatement("select service from queue_services where q_no = (select s_no from services where service = '" + ql.get(i) + "')").executeQuery();
                            while (gs.next()) {
                                servicesSelction.getItems().addAll(gs.getString("service"));
                            }
                            servicesSelction.getSelectionModel().selectFirst();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

        pool.releaseConnection(con);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JFXButton trans = new JFXButton("Transfer");
        trans.setButtonType(JFXButton.ButtonType.RAISED);
        trans.setPrefSize(80, 40);
        trans.getStylesheets().clear();
        trans.getStylesheets().add("/styles/Style-Default.css");
        trans.getStyleClass().clear();
        trans.getStyleClass().add("jfx-button");
        trans.setTextAlignment(TextAlignment.CENTER);
        
        JFXButton cancel = new JFXButton("Cancel");
        cancel.setCancelButton(true);
        cancel.setButtonType(JFXButton.ButtonType.RAISED);
        cancel.setPrefSize(80, 40);
        cancel.getStylesheets().clear();
        cancel.getStylesheets().add("/styles/Style-Default.css");
        cancel.getStyleClass().clear();
        cancel.getStyleClass().add("jfx-button");
        cancel.setTextAlignment(TextAlignment.CENTER);
        
        HBox hb = new HBox(20, trans,cancel);
        hb.setAlignment(Pos.CENTER);
        
        VBox vb = new VBox(25,queueSelection,servicesSelction,hb);
        vb.setPrefSize(250, 250);
        vb.setAlignment(Pos.CENTER);
        
        JFXDialogLayout lay = new JFXDialogLayout();
        lay.setHeading(h);
        lay.setBody(vb);
        
        JFXAlert<Pair<String,String>> al = new JFXAlert<>();
        al.setContent(vb);
        al.setAnimation(JFXAlertAnimation.SMOOTH);
        
        cancel.setOnAction((t) -> {
            al.close();
        });
        
        trans.setOnAction((t) -> {
            if (!queueSelection.getSelectionModel().isEmpty()) {
                try {
                    List<QueueServices> service_list = d.listQueueServices();
                    List<Service> queue_list = d.listServices();
                    for (int i = 0; i < queue_list.size(); i++) {
                        if (queueSelection.getSelectionModel().getSelectedItem().toString().equals(queue_list.get(i).getServiceName())) {
                            for (int j = 0; j < service_list.size(); j++) {
                                if (servicesSelction.getSelectionModel().getSelectedItem().toString().equals(service_list.get(j).getQueueServiceName())) {
                                    al.setResult(new Pair<>(queue_list.get(i).getServiceName(), service_list.get(j).getQueueServiceName()));
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                createAlert(AlertType.ERROR, "Error", "Cannot Transfer Ticket", "Please select a queue and service to transfer to");
            }
        });
        
        al.setResultConverter((p) -> { 
            return null; //To change body of generated lambdas, choose Tools | Templates.
        });
        
        Optional<Pair<String,String>> res = al.showAndWait();
        
        return res;
    }
    
    public void transferButtonActionToPerform(ListView allListView) {
        try {
            if (!allListView.getItems().isEmpty()) {
                String v = allListView.getItems().get(allListView.getItems().size() - 1).toString();
                if (v != null) {
                    String vtag = v.substring(0, 1);
                    String vtn = v.substring(1, v.indexOf(" "));
                    String vticket = vtag + vtn;
                    String time_done = "";
                    Connection connect = pool.getConnection();
                    PreparedStatement locstmt = connect.prepareStatement("select time_done from tickets where tag = '" + vtag + "' and t_no = '" + vtn + "' and t_date='" + local_date + "' limit 1");
                    ResultSet rs = locstmt.executeQuery();
                    while (rs.next()) {
                        time_done = rs.getString("time_done");
                    }
                    if (time_done == null) {
                        Optional<Pair<String, String>> transferPair = transferChoiseDialogWithServices();
                        if(transferPair.isPresent()){
                        if (transferPair.get().getKey() != null) {
                            List<Service> l = d.listServices();
                            for(int i=0;i<l.size();i++){
                                if(transferPair.get().getKey().equals(l.get(i).getServiceName())){
                                    transferTicket(vtag, vtn, l.get(i).getServiceNo(), transferPair.get().getValue());
                                    System.out.println(vticket + " Transfered to "+ l.get(i).getServiceName());
                                    allListView.getItems().remove(allListView.getItems().size() - 1);
                                }
                            }
                        }
                        }
                    } else {
                        createAlert(Alert.AlertType.WARNING, "Error", "This ticket was called already", null);
                    }
                    pool.releaseConnection(connect);
                } else {
                    createAlert(Alert.AlertType.WARNING, "Empty Queue", "No ticket on queue", null);
                }
            } else {
                createAlert(Alert.AlertType.WARNING, "Error", "No ticket called", null);
            }
        } catch (SQLException ex) {ex.printStackTrace();}
    }
    
    void transferTicket(String vtag, String vtn, String tag, String service) {
        try {
            Connection connect = pool.getConnection();
            Statement stmt = connect.createStatement();
            String tr1 = vtag;
            String tr2 = vtn;
            String tr3 = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
            String cmd = "insert into transfer (tag,t_no, trans_to, time_trans, service, t_date) values('" + tr1 + "','" + tr2 + "', '" + tag + "','" + tr3 + "','" + service + "', '" + local_date + "')";
            stmt.executeUpdate(cmd);
            String updTransferred = "update tickets set time_done = '" + tr3 + "', transferred = TRUE where tag = '" + tr1 + "' and t_no = '" + tr2 + "' and t_date='" + local_date + "' ";
            stmt.executeUpdate(updTransferred);
            System.out.println("Transfer Table Updated");
            pool.releaseConnection(connect);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void callAgainActionToPerform(ListView allListView, Label currentlyServing) {
        try {
            Connection con = pool.getConnection();
            if (!allListView.getItems().isEmpty()) {
                String gettkt = allListView.getItems().get(allListView.getItems().size() - 1).toString();
                String tkt1= gettkt.substring(0, gettkt.indexOf(" "));
                ResultSet rs = con.prepareStatement("select time_done from tickets where tag = '" + gettkt.substring(0, 1) + "' and t_no = '" + gettkt.substring(1, gettkt.indexOf(" ")) + "' and t_date='" + local_date + "' limit 1").executeQuery();
                while (rs.next()) {
                    String t_done = rs.getString("time_done");
                    if (t_done == null) {
                        currentlyServing.setText(tkt1);
                        flash(currentlyServing);
                        callTicketToDisplay(tkt1);
                    } else {createAlert(AlertType.WARNING, "Error Calling Ticket", "Ticket cannot be called again", null);}
                }
            } else {createAlert(AlertType.WARNING, "Empty Queue", "Queue is empty", "Empty Queue");}
            pool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void callMissedActionToPerform(ListView allListView, Label currentlyServingTicketNumber, Label missedNoLabel) {
        try {
            Connection con = pool.getConnection();
            PreparedStatement call_client = con.prepareStatement("select tag, t_no, service from tickets where missing_client is not null and time_called is null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and t_date='" + local_date + "' limit 1");
            ResultSet cc = call_client.executeQuery();
            if (cc.next()) {
                if (allListView.getItems().size() > 2) {
                    allListView.getItems().remove(0);
                }
                String vtag = cc.getString("tag");
                String vtn = cc.getString("t_no");
                String service_local = cc.getString("service");
                Statement stmt = con.createStatement();
                currentlyServingTicketNumber.setText(vtag + vtn);
                callTicketToDisplay(vtag + vtn);
                allListView.getItems().add(vtag + vtn + " - " + service_local);
                String currentTime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
                stmt.executeUpdate("update tickets set time_called = '" + currentTime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and t_date='" + local_date + "'");
                PreparedStatement count = con.prepareStatement("select count(*) from tickets where missing_client is not null and time_called is null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and t_date='" + local_date + "' ");
                ResultSet counter = count.executeQuery();
                while (counter.next()) {
                    String value = counter.getString("count(*)");
                    missedNoLabel.setText(value);
                }
                if (!allListView.getItems().isEmpty() && allListView.getItems().size() > 1) {
                    String v = allListView.getItems().get(allListView.getItems().size() - 2).toString();
                    String vtag2 = v.substring(0, 1);
                    String vtn2 = v.substring(1, v.indexOf(" "));
                    if (vtag2.equals(vtag)) {
                        stmt.executeUpdate("update tickets set time_done= '" + currentTime + "' where tag = '" + vtag2 + "' and t_no = '" + vtn2 + "' and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and time_done is null and t_date='" + local_date + "'");
                    } else {
                        stmt.executeUpdate("update transfer set time_done= '" + currentTime + "' where tag = '" + vtag2 + "' and t_no = '" + vtn2 + "' and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and time_done is null and t_date='" + local_date + "'");
                    }
                }
            } else {
                createAlert(AlertType.WARNING, "Empty Queue", "Missed queue is empty", "Empty Queue");
            }
            pool.releaseConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void doneButtonPerformAction(ListView allListView, String tag) {
        try {
            if (!allListView.getItems().isEmpty()) {
                String v = allListView.getItems().get(allListView.getItems().size() - 1).toString();
                if (v != null) {
                    String localtime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
                    Connection connect = pool.getConnection();
                    String vtag = v.substring(0, 1);
                    String vtn = v.substring(1, v.indexOf(" "));
                    Statement stmt = connect.createStatement();
                    if (vtag.equals(tag)) {
                        stmt.executeUpdate("update tickets set time_done = '" + localtime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and time_done is null and t_date='" + local_date + "' ");
                    } else {
                        stmt.executeUpdate("update transfer set time_done = '" + localtime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and time_done is null and t_date='" + local_date + "' ");
                    }
                    pool.releaseConnection(connect);
                } else {
                    createAlert(AlertType.WARNING, "Empty Queue", "No ticket on queue", "Empty Queue");
                }
            } else {
                createAlert(AlertType.WARNING, "Empty Queue", "No ticket called", "Empty Queue");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<String> showLockTimeSpinner(ToggleButton lockbtn) {
        
        JFXTimePicker tp  =new JFXTimePicker(LocalTime.now());
        tp.setDefaultColor(Paint.valueOf("#2E315B"));
        tp.getEditor().setStyle("-fx-text-fill: #2E315B; -fx-prompt-text-fill: #2E315B;");
        
        Label h = new Label("Choose Time");
        h.setStyle("-fx-text-fill: #2E315B;-fx-font-size:15px;");
        
        JFXButton cancel = new JFXButton("Cancel");
        cancel.setCancelButton(true);
        JFXButton set = new JFXButton("Lock");
        set.setDefaultButton(true);
        HBox hb = new HBox(10,set,cancel);
        hb.setAlignment(Pos.CENTER);
        
        VBox vb = new VBox(20, h,tp,hb);
        vb.setAlignment(Pos.CENTER);
        vb.setPrefSize(300, 200);
        vb.getStylesheets().clear();
        vb.getStylesheets().add("/styles/Style-Default.css");
        
        JFXAlert<String> al = new JFXAlert<>();
//        JFXDialogLayout l =  new JFXDialogLayout();
//        l.setBody(vb);
        al.setContent(vb);
        al.setAnimation(JFXAlertAnimation.NO_ANIMATION);
        //JFXDialog dia = new JFXDialog((StackPane)this.getParent(), vb, JFXDialog.DialogTransition.CENTER);
        
        cancel.setOnAction((t) -> {
            lockbtn.setSelected(false);
            al.setResult(null);
            al.close();
        });
        set.setOnAction((t) -> {
            al.setResult(tp.getValue().toString()+":00");
            System.out.println(tp.getValue().toString()+":00");
            lockbtn.setSelected(true);
            al.close();
        });
        al.setResultConverter((p) -> {
            if(p == ButtonType.CANCEL){
                lockbtn.setSelected(false);
                return null;
            }
            return null; //To change body of generated lambdas, choose Tools | Templates.
        });
        Optional<String> res =  al.showAndWait();
        return res;
        
    }
    
    public String changeStringFormat(String stringToChange) {
        if (stringToChange.length() == 1) {
            return "0" + stringToChange;
        } else {
            return stringToChange;
        }
    }
    
    public void lockButtonPerformAction(ToggleButton lockTbtn, String service_no) {
        try {
            Connection con = pool.getConnection();
            Statement stmt = con.createStatement();
            List<Service> l =d.listServices();
            Optional<String> o  =showLockTimeSpinner(lockTbtn);
            String lock_result = null;
            if(o.isPresent())
                lock_result = o.get();
            String unlock_time = null;
            if (lock_result != null) {
                if (LocalTime.parse(lock_result).isBefore(LocalTime.now())) {
                    createAlert(AlertType.ERROR, "Error", "Time is after current time", "Could not lock service");
                    lockTbtn.setSelected(false);
                } else {
                    unlock_time = lock_result;
                    
                    for(int i = 0; i<l.size();i++){
                    if(l.get(i).getServiceNo().equals(service_no)){
                        if(lockTbtn.isSelected()){
                            if(l.get(i).getLocked() == false){
                                lockTbtn.setText("Unlock");
                                stmt.executeUpdate("update services set locked = '1', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "', unlock_time = '" + unlock_time + "' where s_no = '" + service_no + "'");
                            }else {
                                createAlert(AlertType.WARNING, "Queue Locked", "Queue was already locked by " + l.get(i).getLockedByStaff() + "!", "Queue is already locked");
                            }
                        }else {
                            lockTbtn.setText("Lock");
                            stmt.executeUpdate("update services set locked = '0', unlock_time = null where s_no = '" + service_no + "'");
                        }
                    }
                    }
                }
                
            }else{
                lockTbtn.setSelected(false);
            }
            pool.releaseConnection(con);
        } catch (SQLException e) {
        }
    }
    
    public void callTransferedActionToPerform(ListView allListView, Label currentlyServing, String tag) {
        try {
            Connection con = pool.getConnection();
            Statement stmt2 = con.createStatement();
            //first lock the Ticket
            stmt2.executeUpdate("update transfer set locked = true, staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where time_called is null and locked is null and trans_to = '" + tag + "' and t_date='" + local_date + "' limit 1");
            //call Ticket
            PreparedStatement call_ticket = con.prepareStatement("select tag, t_no from transfer where time_called is null and trans_to = '" + tag + "' and locked is not null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and t_date='" + local_date + "' limit 1");
            String currentTime = String.valueOf(LocalTime.now().minusHours(1)).substring(0, 8);
            ResultSet rs = call_ticket.executeQuery();
            if (rs.next()) {
                if (!allListView.getItems().isEmpty()) {
                    if (allListView.getItems().size() > 2) {
                        allListView.getItems().remove(0);
                    }
                    String v = allListView.getItems().get(allListView.getItems().size() - 1).toString();
                    String vtag = v.substring(0, 1);
                    String vtn = v.substring(1, v.indexOf(" "));
                    PreparedStatement transferred = con.prepareStatement("select transferred from tickets where tag = '" + vtag + "' and t_no = '" + vtn + "' and t_date='" + local_date + "' ");
                    ResultSet rs2 = transferred.executeQuery();
                    if (rs2.next()) {
                        String transfer_local = rs2.getString("transferred");
                        if (transfer_local != null) {
                            String updTimeDone = "update transfer set time_done= '" + currentTime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and t_date='" + local_date + "' ";
                            stmt2.executeUpdate(updTimeDone);
                        } else {
                            String updTimeDone = "update tickets set time_done= '" + currentTime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and t_date='" + local_date + "' ";
                            stmt2.executeUpdate(updTimeDone);
                        }
                    }
                }
                String vtag = rs.getString("tag");
                int vt_no = rs.getInt("t_no");
                String trnstkt = vtag + vt_no;
                PreparedStatement getService = con.prepareStatement("select service from transfer where tag = '" + vtag + "' and t_no = '" + vt_no + "' and t_date='" + local_date + "' ");
                ResultSet gs = getService.executeQuery();
                String service_local = "";
                while (gs.next()) {
                    service_local = gs.getString("service");
                }
                stmt2.executeUpdate("update transfer set time_called = '" + currentTime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where tag = '" + vtag + "' and t_no = '" + vt_no + "' and t_date='" + local_date + "'");
                currentlyServing.setText(trnstkt);
                callTicketToDisplay(trnstkt);
                allListView.getItems().add(new Ticket(vt_no, vtag) + " - " + service_local);
            } else {
                createAlert(AlertType.WARNING, "Empty Queue", "Transfered queue is empty", "Empty Queue");
            }
            pool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
