/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.models;

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
import java.util.prefs.Preferences;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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
    
    Button noShow = new Button("No Show");
    Button next = new Button("Next");
    Button transfer = new Button("Transfer");
    Button done = new Button("Done");
    Button callMissed = new Button("Call Missed");
    Button callTrans = new Button("Call Trans");
    Button callAgain = new Button("Call Again");
    public ToggleButton lock = new ToggleButton("Lock");
    Button b = new Button("b");
    
    Label missedLabel = new Label("Missed");
    Label missedCounterLabel = new Label("0");
    Label allLabel = new Label("All");
    Label transLabel = new Label("Transfered");
    public Label transferCounterLabel = new Label("0");
    Label currentlySerVingLabel = new Label("");
    Label serviceName = new Label();
    public Label noInlineLabel = new Label("33",new ImageView("/img/icons8-queue-32.png"));
    
    ImageView missedIcon  = new ImageView("/img/170-512.png");
    ImageView transferIcon  = new ImageView("/img/icons8-exchange-802.png");
    ImageView ticketIcon = new ImageView("/img/currentservingticket.png");

    CheckBox autoCB= new CheckBox("Auto Call Transfered");
    
    ListView lv = new ListView();
     
    private void init(){
        
        noShow.setPrefSize(100, 50);
        next.setPrefSize(100, 50);
        transfer.setPrefSize(100, 50);
        done.setPrefSize(100, 50);
        callAgain.setPrefSize(100, 50);
        callMissed.setPrefSize(100, 50);
        callTrans.setPrefSize(100, 50);
        lock.setPrefSize(100, 50);
        b.setPrefSize(100, 50);
        
        missedIcon.setPreserveRatio(true);
        missedIcon.setSmooth(true);
        missedIcon.setFitHeight(50);
        missedIcon.setFitWidth(50);
        transferIcon.setFitHeight(50);
        transferIcon.setFitWidth(50);
        transferIcon.setPreserveRatio(true);
        
        
        ticketIcon.setPreserveRatio(true);
        ticketIcon.setSmooth(true);
        ticketIcon.setFitHeight(160);
        ticketIcon.setFitWidth(200);
        ticketIcon.setLayoutX(390);
        ticketIcon.setLayoutY(70);
        ticketIcon.relocate(415, 55);
        
        currentlySerVingLabel.setAlignment(Pos.CENTER);
        currentlySerVingLabel.relocate(415, 60);
        currentlySerVingLabel.setPrefSize(200, 160);
        currentlySerVingLabel.setTextAlignment(TextAlignment.CENTER);
        currentlySerVingLabel.setFont(Font.font(50));
        currentlySerVingLabel.setId("tlable");
        
        serviceName.setTextAlignment(TextAlignment.CENTER);
        serviceName.setAlignment(Pos.CENTER);
        serviceName.setText(name);
        serviceName.setFont(new Font(25));
        serviceName.setTextFill(Paint.valueOf("white"));
        serviceName.setId("title");
        
        noInlineLabel.setAlignment(Pos.CENTER);
        noInlineLabel.setTextFill(Paint.valueOf("white"));
        serviceName.setAlignment(Pos.CENTER);
        allLabel.setTextAlignment(TextAlignment.CENTER);
        missedLabel.setTextAlignment(TextAlignment.CENTER);
        transLabel.setTextAlignment(TextAlignment.CENTER);
        
        autoCB.setTextFill(Paint.valueOf("white"));
        autoCB.setAlignment(Pos.CENTER);
        autoCB.setSelected(true);
        autoCB.setManaged(true);
                
        lv.setPrefSize(165, 95);
        lv.setLayoutX(146);
        lv.setLayoutY(102);
        
        noInlineLabel.relocate(10, 30);
        serviceName.relocate(150, 30);
        autoCB.relocate(450, 30);
        Region r = new Region();
        r.setPrefSize(130, 50);
        //HBox hb1 = new HBox(30, noInlineLabel,r,serviceName,autoCB);
        
//        HBox shb1 = new HBox(40, missedLabel,allLabel,transLabel);
//        shb1.setAlignment(Pos.CENTER);
        
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
        //hb2.setBackground(new Background(new BackgroundFill(Paint.valueOf("blue"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Region r2= new Region();
        r2.setPrefSize(50, 50);
        HBox hb3 = new HBox(15,noShow,next,transfer,done);
        HBox hb4 = new HBox(15, callMissed,callAgain,callTrans,lock);
        VBox vb1 = new VBox(15, hb3,hb4);
        vb1.setPadding(new Insets(20, 0, 0,0));
        //HBox hb5 = new HBox(30, loggedInUser, servingCounterLabel);
        
        VBox layoutVBox = new VBox(15,hb2,vb1);
        
        //hb1.setAlignment(Pos.CENTER);
        //hb2.setAlignment(Pos.CENTER_LEFT);
        hb3.setAlignment(Pos.CENTER);
        hb4.setAlignment(Pos.CENTER);
        vb1.setAlignment(Pos.CENTER);
        layoutVBox.setAlignment(Pos.CENTER);
        layoutVBox.setPadding(new Insets(60, 5, 5, 5));
        layoutVBox.setPrefSize(600, 360);
        
        this.getChildren().addAll(noInlineLabel, serviceName,autoCB,layoutVBox);
        this.getChildren().addAll(ticketIcon,currentlySerVingLabel);
        //layoutVBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("blue"), CornerRadii.EMPTY, Insets.EMPTY)));
        this.getStylesheets().clear();
        this.getStylesheets().add("/styles/Style-Default.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("control-view-pane");
        
        //OnActions
        autoCB.setOnAction((t) -> {
           if(autoCB.isSelected())
               autoCB.setSelected(false);
           else
               autoCB.setSelected(true);
        });
        
        done.setOnAction((t) -> {
            doneButtonPerformAction(lv, this.service.getServiceNo());
        });
        lock.setOnAction((t) -> {
            lockButtonPerformAction(lock, this.service.getServiceNo());
        });
        
        next.setOnAction((t) -> {
           nextButtonActionToPerform(lv, autoCB, currentlySerVingLabel, this.service.getServiceNo());
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
    
    void flash(Label l) {
        FlashTransition ft = new FlashTransition(l);
        ft.play();
    }
    
    public void callTicketToDisplay(String tkt) {
        System.out.println(tkt+" goto counter "+FXMLController.loggedInStaff.getCounter());
    }
    
    void createAlert(Alert.AlertType alertType, String title, String contextText, String headerText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
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
                        String service = "";
                        while (gs.next()) {
                            service = gs.getString("service");
                        }
                        stmt.executeUpdate("update transfer set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where tag = '" + vtag + "' and t_no = '" + vt_no + "' and time_called is null and t_date='" + local_date + "'");
                        allListVIew.getItems().add(new Ticket(vt_no, vtag) + " - " + service);
                        currentlyServingLabel.setText(tkt);
                        flash(currentlyServingLabel);
                        callTicketToDisplay(tkt);
                        stmt.executeUpdate("update tickets set locked = null, staff_no = null where time_called is null and locked is not null and missing_client is null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and tag='" + tag + "' and t_date='" + local_date + "' limit 1");
                    } else {
                        String ntag = rs.getString("tag");
                        int ntn = rs.getInt("t_no");
                        String service = rs.getString("service");
                        String ntkt = ntag + ntn;
                        stmt.executeUpdate("update tickets set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "', alert = '1'  where t_no = '" + ntn + "' and tag = '" + ntag + "' and time_called is null and t_date='" + local_date + "'");
                        allListVIew.getItems().add(new Ticket(ntn, ntag) + " - " + service);
                        currentlyServingLabel.setText(ntkt);
                        flash(currentlyServingLabel);
                        callTicketToDisplay(ntkt);
                        stmt.executeUpdate("update transfer set locked = null, staff_no = null where time_called is null and locked is not null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and trans_to='" + tag + "' and t_date='" + local_date + "' limit 1");
                    }
                } else if (!autoTransferCB.isSelected()) {
                    String ntag = rs.getString("tag");
                    int ntn = rs.getInt("t_no");
                    String service = rs.getString("service");
                    String ntkt = ntag + ntn;
                    stmt.executeUpdate("update tickets set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' , alert = '1' where t_no = '" + ntn + "' and tag = '" + ntag + "' and time_called is null and t_date='" + local_date + "'");
                    allListVIew.getItems().add(new Ticket(ntn, ntag) + " - " + service);
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
                    String service = "";
                    while (gs.next()) {
                        service = gs.getString("service");
                    }
                    stmt.executeUpdate("update transfer set time_called = '" + localtime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where tag = '" + vtag + "' and t_no = '" + vt_no + "' and time_called is null and t_date='" + local_date + "'");
                    allListVIew.getItems().add(new Ticket(vt_no, vtag) + " - " + service);
                    currentlyServingLabel.setText(tkt);
                    flash(currentlyServingLabel);
                    callTicketToDisplay(tkt);
                    stmt.executeUpdate("update tickets set locked = null, staff_no = null where time_called is null and locked is not null and missing_client is null and staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' and tag='" + tag + "' and t_date='" + local_date + "' limit 1");
                } else {
                    createAlert(Alert.AlertType.WARNING, "Empty Queue", "Check auto call transfer box to call transfered ticket automatically", null);
                }
            } else {
                createAlert(Alert.AlertType.WARNING, "Empty Queue", "Queue is empty", null);
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
                            createAlert(Alert.AlertType.WARNING, "Error", "Ticket cannot be added to missed queue", null);
                        }
                    }
                    pool.releaseConnection(con);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                createAlert(Alert.AlertType.WARNING, "Empty queue", "Queue is empty", null);
            }
        } else {
            createAlert(Alert.AlertType.WARNING, "Error calling ticket", "No ticket called", null);
        }
    }
    
    Optional<Pair<String, String>> transferChoiseDialogWithServices() {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Transfer Ticket");
        dialog.setHeaderText("Select queue and service to transfer to");
        dialog.setGraphic(new ImageView(this.getClass().getResource("/img/icons8-exchange-802.png").toString()));
        ButtonType done = new ButtonType("Transfer", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(done, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 200, 10, 10));

        ComboBox queueSelection = new ComboBox();
        ComboBox servicesSelction = new ComboBox();

        grid.add(queueSelection, 1, 0);
        grid.add(servicesSelction, 1, 1);
        dialog.getDialogPane().setContent(grid);

        //String lcb = loginCombo.getSelectionModel().getSelectedItem();
        try {
            Connection con = pool.getConnection();
            List<String> ql = new ArrayList<>();
            ResultSet gq = con.prepareStatement("select service from services where service != '" + "Current Customer" + "'").executeQuery();
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
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == done) {
                    if (queueSelection.getSelectionModel().getSelectedItem().toString() == null) {
                        createAlert(AlertType.ERROR, "Error", "Cannot Transfer Ticket", "Please select a queue and service to transfer to");
                    } else {
                        try {
                            List<QueueServices> service_list = d.listQueueServices();
                            List<Service> queue_list = d.listServices();
//                            ResultSet gq2 = con.prepareStatement("select service from services").executeQuery();
//                            ResultSet gs = con.prepareStatement("select service from queue_services").executeQuery();
//                            while (gq2.next()) {
//                                queue_list.add(gq2.getString("service"));
//                            }
//                            while (gs.next()) {
//                                service_list.add(gs.getString("service"));
//                            }
                            for (int i = 0; i < queue_list.size(); i++) {
                                if (queueSelection.getSelectionModel().getSelectedItem().toString().equals(queue_list.get(i).getServiceName())) {
                                    for (int j = 0; i < service_list.size(); j++) {
                                        if (servicesSelction.getSelectionModel().getSelectedItem().toString().equals(service_list.get(j).getQueueServiceName())) {
                                            return new Pair<>(queue_list.get(i).getServiceName(), service_list.get(j).getQueueServiceName());
                                        }
                                    }
                                }
                            }
                            pool.releaseConnection(con);
                        } catch (Exception ex) {
                            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if (dialogButton == ButtonType.CANCEL) {

                }
                return null;
            });
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Optional<Pair<String, String>> result = dialog.showAndWait();
        
        return result;
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
            } else {createAlert(AlertType.WARNING, "Empty Queue", "Queue is empty", null);}
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
                String service = cc.getString("service");
                Statement stmt = con.createStatement();
                currentlyServingTicketNumber.setText(vtag + vtn);
                callTicketToDisplay(vtag + vtn);
                allListView.getItems().add(vtag + vtn + " - " + service);
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
                createAlert(AlertType.WARNING, "Empty Queue", "Missed queue is empty", null);
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
                    createAlert(AlertType.WARNING, "Empty Queue", "No ticket on queue", null);
                }
            } else {
                createAlert(AlertType.WARNING, "Empty Queue", "No ticket called", null);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    Optional<Pair<String, String>> showLockTimeSpinner(ToggleButton lockbtn) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Lock Service");
        dialog.setHeaderText("Set time to unlock service");
        ButtonType lock = new ButtonType("Lock", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(lock, ButtonType.CANCEL);
        dialog.getDialogPane().setPrefSize(220, 180);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label hourLabel = new Label("Hour");
        Label minuteLabel = new Label("Minute");
        Spinner hourSpinner = new Spinner(LocalTime.now().getHour(), 24, LocalTime.now().getHour());
        Spinner minuteSpinner = new Spinner(0, 59, LocalTime.now().getMinute());

        grid.add(hourLabel, 0, 0);
        grid.add(minuteLabel, 1, 0);
        grid.add(hourSpinner, 0, 1);
        grid.add(minuteSpinner, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == lock) {
                lockbtn.setSelected(true);
                return new Pair<>(hourSpinner.getValue().toString(), minuteSpinner.getValue().toString());
            }
            if (dialogButton == ButtonType.CANCEL) {
                lockbtn.setSelected(false);
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        return result;
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
            Optional<Pair<String, String>> lock_result = showLockTimeSpinner(lockTbtn);
            String unlock_time = null;
            if (lock_result.isPresent()) {
                if (LocalTime.parse(changeStringFormat(lock_result.get().getKey()) + ":" + changeStringFormat(lock_result.get().getValue()) + ":" + "00").isBefore(LocalTime.now())) {
                    createAlert(AlertType.ERROR, "Error", "Time is after current time", "Could not lock service");
                    lockTbtn.setSelected(false);
                } else {
                    unlock_time = lock_result.get().getKey() + ":" + lock_result.get().getValue() + ":" + "00";
                }
            }
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
                        String transfer = rs2.getString("transferred");
                        if (transfer != null) {
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
                String service = "";
                while (gs.next()) {
                    service = gs.getString("service");
                }
                stmt2.executeUpdate("update transfer set time_called = '" + currentTime + "', staff_no = '" + FXMLController.loggedInStaff.getStaffNo() + "' where tag = '" + vtag + "' and t_no = '" + vt_no + "' and t_date='" + local_date + "'");
                currentlyServing.setText(trnstkt);
                callTicketToDisplay(trnstkt);
                allListView.getItems().add(new Ticket(vt_no, vtag) + " - " + service);
            } else {
                createAlert(AlertType.WARNING, "Empty Queue", "Transfered queue is empty", null);
            }
            pool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
