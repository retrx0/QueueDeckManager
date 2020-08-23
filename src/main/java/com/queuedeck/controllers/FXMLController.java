package com.queuedeck.controllers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import com.queuedeck.pool.BasicConnectionPool;
import com.queuedeck.effects.FlashTransition;
import com.queuedeck.effects.ShakeTransition;
import com.queuedeck.effects.FadeInDownTransition;
import com.queuedeck.models.ControlView;
import com.queuedeck.models.DAOInterface;
import com.queuedeck.models.JPAClass;
import com.queuedeck.models.Service;
import com.queuedeck.models.Staff;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import javafx.geometry.Pos;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;

public class FXMLController implements Initializable {

    //<editor-fold defaultstate="collapsed" desc="Global Variables">
    static final String URL_STRING = "jdbc:mysql://104.155.33.7:3306/ticketing";
    static final String USERNAME_STRING = "root";
    static final String PASSWORD_STRING = "rotflmao0000";
    public static BasicConnectionPool pool = BasicConnectionPool.create(URL_STRING, USERNAME_STRING, PASSWORD_STRING);
    String tkt;
    private String staff_level;
    String local_date = LocalDate.now().toString();
    boolean showNewTicketAlert = true;
    DAOInterface d = new JPAClass();
    List<Service> servList = d.listServices();
    List<Staff> staffList = d.listStaff();
    List<ControlView> paneList = new ArrayList<>();
    public static Staff loggedInStaff;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FXML Variables">
    @FXML
    private Label productivityLabel;
    @FXML
    public AnchorPane containerPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private StackPane cardsStackPane;
    @FXML
    private AnchorPane loginNode;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    JFXTextField staffNoTextField;
    @FXML
    JFXComboBox<String> loginCombo;
    @FXML
    private AnchorPane statsNode;
    @FXML
    private Label loginActionLabel;
    @FXML
    public Label loggedInAsLabel;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private PieChart statsPieChart;
    @FXML
    private Label avgServiceTimeLabel;
    @FXML
    private Label avgWaitTimeLabel;
    @FXML
    private Label counterLabel;
    @FXML
    private Label peopleAttentedTO;
    @FXML Menu changeServiceMenu;
    @FXML private CheckMenuItem showNotifcationMenuItem;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Action Methods">
    public ControlView getControlViewWithServiceName(String name){
        ControlView cv = null;
        for (int i = 0; i < paneList.size(); i++) {
            if(paneList.get(i).getService().getServiceName().equals(name)){
                cv = paneList.get(i);
            }
        }
        return cv;
    }
    
    public void close() {
        Platform.runLater(() -> {
            try {
                for (int i = 0; i < paneList.size(); i++) {
                    if (loginCombo.getSelectionModel().getSelectedItem() != null) {
                        if (loginCombo.getSelectionModel().getSelectedItem().equals(servList.get(i).getServiceName())) {
                            addTimeDone(getControlViewWithServiceName(servList.get(i).getServiceName()).lv, servList.get(i).getServiceNo());
                            break;
                        }
                    }
                }
                Connection con = pool.getConnection();
                Statement stmt = con.createStatement();
                stmt.executeUpdate("update staff set online = '0' where staff_no = '" + staffNoTextField.getText() + "'");
                System.out.println(staffNoTextField.getText());
                System.out.println("done");
                pool.releaseConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    void createAlertWhenThereIsATkt() {
        Media notification = new Media(this.getClass().getResource("/sounds/notification.mp3").toString());
        MediaPlayer mp = new MediaPlayer(notification);
        mp.play();
        JFXAlert al  = new JFXAlert();
        al.initModality(Modality.APPLICATION_MODAL);
        al.setOverlayClose(false);
        al.setAnimation(JFXAlertAnimation.NO_ANIMATION);
        
        Label ct = new Label("New Customer on Queue");
        ct.setStyle("-fx-text-fill: #2E315B");
        Label h = new Label("Notification");
        h.setStyle("-fx-text-fill: #2E315B");
        
        VBox vb= new VBox(15, h,ct);
        
        JFXButton doneBtn =  new JFXButton("OK");
        doneBtn.setDefaultButton(true);
        doneBtn.setPrefSize(50, 25);
        
        JFXDialogLayout lay = new JFXDialogLayout();
        lay.setHeading(h);
        lay.setBody(ct);
        lay.setActions(doneBtn);
        JFXDialog di =new JFXDialog(cardsStackPane, lay, JFXDialog.DialogTransition.LEFT);
        doneBtn.setOnAction((t) -> {
           di.close();
        });
        di.show();
    }
    
    public String changeStringFormat(String stringToChange) {
        if (stringToChange.length() == 1) {
            return "0" + stringToChange;
        } else {
            return stringToChange;
        }
    }

    void createAlert(AlertType alertType, String title, String contextText, String headerText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
    
    void createAlert(String title, String contextText, String headerText) {
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
        
        JFXDialog di =new JFXDialog(cardsStackPane, lay, JFXDialog.DialogTransition.LEFT);
        doneBtn.setOnAction((t) -> {
           di.close();
        });
        di.show();
    }
    
    void showMissedTickets(Label missedNoLabel, String tag) {
        try {
            Connection con = pool.getConnection();
            PreparedStatement count = con.prepareStatement("select count(*) from tickets where missing_client is not null and time_called is null and tag = '" + tag + "' and staff_no = '" + staffNoTextField.getText() + "' and t_date='" + local_date + "'");
            ResultSet counter = count.executeQuery();
            while (counter.next()) {
                String value = counter.getString("count(*)");
                missedNoLabel.setText(value);
            }
            pool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void callTicketToDisplay(String tToC) {
        System.out.println(tkt+" goto counter "+loggedInStaff.getCounter());
    }

    public void loginActionToPerform() {
        try {
            //Testing Internet connection
            Process process = java.lang.Runtime.getRuntime().exec("ping www.google.com -n 1");
            int x = process.waitFor();
            if (x != 0) {
                createAlert(AlertType.ERROR, "No internet connection", "No internet connection was detected, internet connection is required to login", "No internet connection detected");
            } else {
                Connection con = pool.getConnection();
                if (passwordTextField.getText().length() != 0 && staffNoTextField.getText().length() != 0) {
                    if (passwordTextField.getText().substring(passwordTextField.getText().length() - 1).equals("\\")) {
                        shake(loginActionLabel);
                        loginActionLabel.setText("Incorrect Staff No or Password");
                    } else {
//                        PreparedStatement get_pswd = con.prepareStatement("select password, count(*), online, staff_name, counter, staff_level from staff where staff_no = '" + staffNoTextField.getText() + "'");
//                        PreparedStatement get_tags = con.prepareStatement("select s_no, service from services");
//                        ResultSet gp = get_pswd.executeQuery();
//                        
//                        ResultSet gt = get_tags.executeQuery();
//                        StaffLevel sl = new StaffLevel();
                        
                        String passwordTextFieldinmd5 = null;
                        ResultSet cp = con.prepareStatement("select md5('" + passwordTextField.getText() + "') as password").executeQuery();
                        while(cp.next()){ passwordTextFieldinmd5 = cp.getString("password");}
                        
                        for(int i=0;i<staffList.size();i++){
                            if(staffList.get(i).getStaffNo().equals(staffNoTextField.getText())){
                                staff_level = ""+staffList.get(i).getStaffLevel();
                                if(staffList.isEmpty()){
                                    shake(loginActionLabel);
                                    loginActionLabel.setText("Incorrect Staff No or Password");
                                }
                                else if(staffList.get(i).getOnline()){
                                    shake(loginActionLabel);
                                    loginActionLabel.setText("Staff Logged In Already");
                                }
                                else if(staffList.get(i).getStaffPassword().equals(passwordTextFieldinmd5)){
                                    if (loginCombo.getSelectionModel().getSelectedItem() == null) {
                                        shake(loginCombo);
                                        shake(loginActionLabel);
                                        loginActionLabel.setText("Please select a service");
                                    }
                                    MenuItem item = null;
                                    List<String> l = d.getServicesForLevel(staff_level);
                                    for (int m = 0; m < changeServiceMenu.getItems().size(); m++) {
                                        changeServiceMenu.getItems().get(m).setDisable(true);
                                        if(l.contains(changeServiceMenu.getItems().get(m).getText()))
                                            changeServiceMenu.getItems().get(m).setDisable(false);
                                        if(changeServiceMenu.getItems().get(m).getText().equals(loginCombo.getSelectionModel().getSelectedItem()))
                                            item = changeServiceMenu.getItems().get(m);
                                    }
                                    for (int j = 0; j < l.size(); j++) {
                                        if(loginCombo.getSelectionModel().getSelectedItem() != null){
                                            if(loginCombo.getSelectionModel().getSelectedItem().equals(l.get(j))){
                                                doFadeinDown(cardsStackPane, menuBar);
                                                doFadeIn(cardsStackPane, d.getControlView(paneList, loginCombo.getSelectionModel().getSelectedItem()));
                                                item.setDisable(true);
                                                con.prepareStatement("update staff set online = '1' where staff_no = '" + staffNoTextField.getText() + "'").executeUpdate();
                                                Preferences p = Preferences.userNodeForPackage(getClass());
                                                p.clear();
                                                p.put("Staff No", staffNoTextField.getText());
                                                staffList = d.listStaff();
                                                for(int k=0;k<staffList.size();k++){
                                                    if(staffNoTextField.getText().equals(staffList.get(k).getStaffNo()))
                                                        loggedInStaff = staffList.get(k);
                                                }
                                                loggedInAsLabel.setText("[" + staffNoTextField.getText() + "] " + loggedInStaff.getStaffName() + "");
                                                counterLabel.setText("serving counter: " + loggedInStaff.getCounter() + "");
                                                showMissedTickets(getControlViewWithServiceName(d.listServices().get(j).getServiceName()).missedCounterLabel, servList.get(j).getServiceNo());//attention
//                                                showMissedTickets(currentCusMissedNoLabel, currentCusTag);
//                                                showMissedTickets(newCusMissedNoLabel, newCusTag);
//                                                showMissedTickets(servicesMissedNoLabel, serviceTag);
//                                                showMissedTickets(otherMissedNoLabel, othersTag);
                                            }else{
                                                shake(loginCombo);
                                                shake(loginActionLabel);
                                                loginActionLabel.setText("Please Select An Appropriate Service");
                                            }
                                        }
                                }
                            }
                                else{
                                    shake(staffNoTextField);
                                    shake(passwordTextField);
                                    shake(loginActionLabel);
                                    loginActionLabel.setText("Incorrect Staff No and/or Password");
                                }
                            }
                        }
                    }
                } else {
                    shake(staffNoTextField);
                    shake(passwordTextField);
                    shake(loginActionLabel);
                    loginActionLabel.setText("Please fill in all the fields");
                }
                pool.releaseConnection(con);
            }
        } catch (IOException | InterruptedException | SQLException | BackingStoreException e) {
            e.printStackTrace();
        }
    }

    public void addTimeDone(ListView allListView, String tag) {
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
                        stmt.executeUpdate("update tickets set time_done = '" + localtime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and time_done is null and t_date='" + local_date + "'");
                    } else {
                        stmt.executeUpdate("update transfer set time_done = '" + localtime + "' where tag = '" + vtag + "' and t_no = '" + vtn + "' and time_done is null and t_date='" + local_date + "'");
                    }
                    pool.releaseConnection(connect);
                }
            }
        } catch (SQLException ex) {
        }
    }

    public void changeServicePerformAction(String itemToSelect, Node nodeToShow) {
        loginCombo.getSelectionModel().select(itemToSelect);
        String selecteditem = loginCombo.getSelectionModel().getSelectedItem();
        doFadeIn(cardsStackPane, nodeToShow);
        List ls = d.getServicesForLevel(staff_level);
        for (int i = 0; i < changeServiceMenu.getItems().size(); i++) {
            if (ls.contains(changeServiceMenu.getItems().get(i).getText())) {
                changeServiceMenu.getItems().get(i).setDisable(false);
            }
            if (changeServiceMenu.getItems().get(i).getText().equals(selecteditem)) {
                changeServiceMenu.getItems().get(i).setDisable(true);
            }
        }
    }

    private static void showNode(StackPane sp, Node nodeToShow) {
        sp.getChildren().clear();
        sp.getChildren().add(nodeToShow);
    }
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Transitions and Animations">
    protected static final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);

    void shake(Node n) {
        ShakeTransition st = new ShakeTransition(n);
        st.play();
    }

    void flash(Node l) {
        FlashTransition ft = new FlashTransition(l);
        ft.play();
    }

    void doSlideInFromTop(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateYProperty().set(-1 * stackPane.getHeight());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv = new KeyValue(paneToAdd.translateYProperty(), 0, WEB_EASE);
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        Timeline tl = new Timeline(kf);
        tl.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        tl.play();
    }

    void doFadeInUpTransition(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateYProperty().set(-1 * stackPane.getHeight());
        stackPane.getChildren().add(paneToAdd);
        Timeline tl = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(paneToAdd.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(paneToAdd.translateYProperty(), 20, WEB_EASE)
                ),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(paneToAdd.opacityProperty(), 1, WEB_EASE),
                        new KeyValue(paneToAdd.translateYProperty(), 0, WEB_EASE)
                ));
        tl.setOnFinished(evt -> stackPane.getChildren().remove(paneToRemove));
        tl.play();
    }

    void doFadeinDown(StackPane stackPane, MenuBar menuBar) {
        FadeInDownTransition fid = new FadeInDownTransition(menuBar);
        menuBar.setVisible(true);
        fid.play();
    }

    void doFadeIn(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        stackPane.getChildren().add(paneToAdd);
        FadeTransition ft = new FadeTransition(Duration.millis(300));
        ft.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        ft.setNode(paneToAdd);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    void doSlideInFromLeft(StackPane stackPane, Node paneToAdd) {
        Node paneToRemove = stackPane.getChildren().get(0);
        paneToAdd.translateXProperty().set(-1 * stackPane.getWidth());
        stackPane.getChildren().add(paneToAdd);
        KeyValue kv = new KeyValue(paneToAdd.translateXProperty(), 0, Interpolator.SPLINE(0.25, 0.1, 0.25, 1));
        KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        Timeline timeline = new Timeline(kf);
        timeline.setOnFinished(evt -> {
            stackPane.getChildren().remove(paneToRemove);
        });
        timeline.play();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Options Menu">
    @FXML
    public void logoutMenuActionPerformed() {
        try {
            for (int i = 0; i < servList.size(); i++) {
                if(loginCombo.getSelectionModel().getSelectedItem().equals(servList.get(i).getServiceName()))
                    addTimeDone(paneList.get(i).lv, servList.get(i).getServiceNo());
                changeServiceMenu.getItems().get(i).setDisable(false);
                paneList.get(i).lv.getItems().clear();
            }
            showNode(cardsStackPane, loginNode);
            passwordTextField.clear();
            Connection con = pool.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("update staff set online = '0' where staff_no = '" + staffNoTextField.getText() + "'");
            staffList = d.listStaff();
            loginActionLabel.setText("");
            loggedInAsLabel.setText("");
            counterLabel.setText("");
            menuBar.setVisible(false);
            pool.releaseConnection(con);
        } catch (SQLException se) {
        }
    }

    @FXML
    void changeCounterActionPerformed() {  
        JFXTextField tf = new JFXTextField("");
        tf.setLabelFloat(true);
        tf.setPromptText("Type new counter");
        tf.setStyle("-fx-text-fill: #2E315B;-fx-prompt-text-fill: #2E315B");
        tf.setAlignment(Pos.CENTER);
        tf.setPadding(new Insets(10));
        Label l1 =new Label("Change Counter");
        l1.setStyle("-fx-text-fill: #2E315B;-fx-font-size:15px;");
        l1.setAlignment(Pos.CENTER);
        l1.setTextAlignment(TextAlignment.CENTER);
        JFXButton done = new JFXButton("Done");
        JFXButton cancel = new JFXButton("Cancel");
        HBox hb = new HBox(10, done,cancel);
        hb.setAlignment(Pos.CENTER);
        VBox vb = new VBox(20,l1,tf,hb);
        vb.setPadding(new Insets(10));
        vb.setPrefSize(210, 160);
        JFXDialog dia = new JFXDialog(cardsStackPane, vb, JFXDialog.DialogTransition.CENTER);
        dia.getStylesheets().clear();
        dia.getStylesheets().add("/styles/Style-Default.css");
        dia.show();
        
        done.setOnKeyPressed((KeyEvent t) -> {
           if(t.getCode() == KeyCode.ENTER)
               done.fire();
        });
        
        done.setOnAction((t) -> {
            if(!tf.getText().isBlank()){
                try{
                    boolean counterInUse = false;
                    Connection con = pool.getConnection();
                    for (int i = 0; i < staffList.size(); i++) {
                        if(tf.getText().equals(""+staffList.get(i).getCounter())){
                            dia.close();
                            createAlert(AlertType.WARNING, "Error", "Counter in use", null);
                            dia.show();
                            counterInUse = true;
                        }
                    }
                    if(!counterInUse){
                        if(Integer.valueOf(tf.getText()) > 0 && Integer.valueOf(tf.getText()) < 20){
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate("update staff set counter ='" + tf.getText()+ "' where staff_no = '" + staffNoTextField.getText() + "'");
                            counterLabel.setText("serving counter: " + tf.getText());
                            flash(counterLabel);
                            dia.close();
                            createAlert(AlertType.INFORMATION, "Counter Changed", "Counter was successfully changed", null);
                        }else{
                            dia.close();
                            createAlert(AlertType.ERROR, "Error", "Input Error!", null);
                            dia.show();
                        }
                    }
                    pool.releaseConnection(con);
                }
                catch(NumberFormatException | SQLException e){System.out.println(e);createAlert(AlertType.ERROR, "Error", "Input Error!", null);changeCounterActionPerformed();}
            }else{
                shake(tf);
            }
        });
        
        cancel.setOnAction((t) -> {
            dia.close();
        });
    }

    @FXML
    void changePasswordActionPerformed() {
        Connection con = pool.getConnection();
        
        Label cpl = new Label("Change Password");
        cpl.setStyle("-fx-text-fill: #2E315B;-fx-text-alignment: center;-fx-font-size: 15px;");
        cpl.setAlignment(Pos.CENTER);
        
        JFXPasswordField cps  = new JFXPasswordField();
        cps.setPromptText("Current Password");
        cps.setStyle("-fx-text-fill: #2E315B;-fx-prompt-text-fill: #2E315B");
        cps.setLabelFloat(true);
        cps.setPadding(new Insets(15));
        
        JFXPasswordField nps  = new JFXPasswordField();
        nps.setPromptText("New Password");
        nps.setStyle("-fx-text-fill: #2E315B;-fx-prompt-text-fill: #2E315B");
        nps.setLabelFloat(true);
        nps.setPadding(new Insets(15));
        
        JFXPasswordField cnps  = new JFXPasswordField();
        cnps.setPromptText("Confirm Password");
        cnps.setStyle("-fx-text-fill: #2E315B;-fx-prompt-text-fill: #2E315B");
        cnps.setLabelFloat(true);
        cnps.setPadding(new Insets(15));
        
        Label al = new Label("");
        al.setStyle("-fx-text-fill: red; -fx-font-size: 15px;");
        al.setAlignment(Pos.CENTER);
        
        JFXButton change = new JFXButton("Change");
        JFXButton cancel = new JFXButton("Cancel");
        HBox hb = new HBox(20, change,cancel);
        hb.setAlignment(Pos.CENTER);
        
        VBox vb = new VBox(10, cpl,cps,nps,cnps,al,hb);
        vb.setPrefSize(350, 250);
        vb.setPadding(new Insets(15));
        vb.setAlignment(Pos.CENTER);
        
        JFXDialog dia = new JFXDialog(cardsStackPane, vb, JFXDialog.DialogTransition.CENTER);
        //dia.setPrefSize(400, 400);
        dia.getStylesheets().clear();
        dia.getStylesheets().add("/styles/Style-Default.css");
        
        Platform.runLater(() -> {
            cps.requestFocus();
        });
        
        cancel.setOnAction((t) -> {
           dia.close();
        });
        
        cnps.setOnKeyPressed((t) -> {
           if(t.getCode() == KeyCode.ENTER)
               change.fire();
        });
        
        nps.setOnKeyTyped((t) -> {
           al.setText("");
        });
        
        change.setOnAction((t) -> {
            try {
                ResultSet cp = con.prepareStatement("select md5('" + cps.getText() + "') as password").executeQuery();
                String cp1 = "";
                while (cp.next()) {
                    cp1 = cp.getString("password");
                }
                if(loggedInStaff.getOnline() == true) {
                    if (cp1.equals(loggedInStaff.getStaffPassword())) {
                        if (nps.getText().equals(cnps.getText())) {
                            if (nps.getText().length() > 4) {
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate("update staff set password = md5('" + nps.getText() + "') where staff_no = '" + staffNoTextField.getText() + "'");
                                createAlert(AlertType.INFORMATION, "Success", "Password successfully changed", null);
                                dia.close();
                            } else {
                                shake(al);
                                al.setText("Password too short!");
                            }
                        } else {
                            shake(nps);shake(cnps);
                            shake(al);
                            al.setText("Passwords do not match!");
                        }
                    } else {
                        shake(cps);
                        shake(al);
                        al.setText("Current password not correct!");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        dia.show();
        pool.releaseConnection(con);
    }
    
    boolean plotPie = false;
    boolean plotLine = false;

    @FXML
    void statsButtonPressed() {
        try {
            doFadeIn(cardsStackPane, statsNode);
            menuBar.setVisible(false);
            Connection con = pool.getConnection();
            int timeNow = LocalTime.now().getHour();
            int num_of_tickets = 0;
            int start = 8;
            XYChart.Series dataSeries1 = new XYChart.Series();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            if (plotLine = true) {
                lineChart.getData().clear();
                lineChart.layout();
                plotLine = false;
            }
            int ia = 0;
            while (start <= timeNow) {
                String hour = String.valueOf(start);
                ResultSet rsCount = con.prepareStatement("select (CASE WHEN time_done LIKE '" + hour + ":%' THEN '" + hour + "' END) as hour, count(*) as num_of_tickets from tickets where staff_no = '" + staffNoTextField.getText() + "' and t_date='" + local_date + "' group by (CASE WHEN time_done LIKE '" + hour + ":%' THEN '" + hour + "' END)").executeQuery();
                while (rsCount.next()) {
                    String time_done = rsCount.getString("hour");
                    if (time_done != null) {
                        num_of_tickets = rsCount.getInt("num_of_tickets");
                    } else {
                        num_of_tickets = 0;
                    }
                }
                dataSeries1.getData().add(ia, new XYChart.Data(hour, num_of_tickets));
                ia++;
                start += 1;
            }
            System.out.println(dataSeries1.getData());
            if (plotLine == false) {
                lineChart.getData().add(dataSeries1);
            }
            PreparedStatement service_count = con.prepareStatement("select tag, count(tag) as amount from tickets where staff_no = '" + staffNoTextField.getText() + "' and t_date='" + local_date + "' group by tag;");
            ResultSet rsSCount = service_count.executeQuery();

            if (plotPie == true) {
                //PreparedStatement get_tag_count = con.prepareStatement("select max(t_no) from tickets where tag = 'A' and staff_no = 'admin'")
                //statsPieChart.getData().add(new PieChart.Data(service, amount));
                pieChartData.clear();
                statsPieChart.getData().clear();
                plotPie = false;
            }
            while (rsSCount.next()) {
                String tag = rsSCount.getString("tag");
                String service = "";
                float amount = rsSCount.getFloat("amount");
                for (int i = 0; i < servList.size(); i++) {
                if(plotPie == false){
                    if(tag.equals(servList.get(i).getServiceName())){
                        service = servList.get(i).getServiceName();
                    }
                    pieChartData.add(new PieChart.Data(service, amount));
                    }
                }
            }
            if (plotPie == false) {
                statsPieChart.getData().addAll(pieChartData);
                final Label caption = new Label("");
                caption.setTextFill(Color.WHITE);
                caption.setStyle("-fx-font: 24 arial;");

                statsPieChart.getData().forEach((data) -> {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()) + "%");
                    });
                });
                plotPie = true;
            }
            //average wait time and average service time
            //tickets table
            ResultSet rs = con.prepareStatement("select time_created, time_called from tickets where staff_no = '" + staffNoTextField.getText() + "' and time_called is not null and t_date='" + local_date + "'").executeQuery();
            ResultSet rs2 = con.prepareStatement("select time_called, time_done from tickets where staff_no = '" + staffNoTextField.getText() + "' and time_done is not null and t_date='" + local_date + "'").executeQuery();
            //transfer table
            ResultSet rs3 = con.prepareStatement("select time_trans, time_called from transfer where staff_no = '" + staffNoTextField.getText() + "' and time_called is not null and t_date='" + local_date + "'").executeQuery();
            ResultSet rs4 = con.prepareStatement("select time_called, time_done from transfer where staff_no = '" + staffNoTextField.getText() + "' and time_done is not null and t_date='" + local_date + "' ").executeQuery();

            int count = 0;
            int count2 = 0;
            int wait_time = 0;
            int service_time = 0;
            //tickets wait time
            while (rs.next()) {
                String time_created = rs.getString("time_created");
                String time_called = rs.getString("time_called");
                int created = LocalTime.parse(time_created).toSecondOfDay();
                int called = LocalTime.parse(time_called).toSecondOfDay();
                int minusTime = called - created;
                wait_time += minusTime;
                count += 1;
            }
            //transfer wait time
            while (rs3.next()) {
                String time_trans = rs3.getString("time_trans");
                String time_called = rs3.getString("time_called");
                int trans = LocalTime.parse(time_trans).toSecondOfDay();
                int called = LocalTime.parse(time_called).toSecondOfDay();
                int minusTime = called - trans;
                wait_time += minusTime;
                count += 1;
            }
            // tickets service time
            while (rs2.next()) {
                String time_done = rs2.getString("time_done");
                String time_called = rs2.getString("time_called");
                int done = LocalTime.parse(time_done).toSecondOfDay();
                int called = LocalTime.parse(time_called).toSecondOfDay();
                int minusTime = done - called;
                service_time += minusTime;
                count2 += 1;
            }
            // transfer service time
            while (rs4.next()) {
                String time_done = rs4.getString("time_done");
                String time_called = rs4.getString("time_called");
                int done = LocalTime.parse(time_done).toSecondOfDay();
                int called = LocalTime.parse(time_called).toSecondOfDay();
                int minusTime = done - called;
                service_time += minusTime;
                count2 += 1;
            }
            int avgWaitTime = 0;
            if (count > 0) {
                avgWaitTime = wait_time / count;
            }
            long avgServiceTime = 0;
            if (count2 > 0) {
                avgServiceTime = service_time / count2;
            }
            LocalTime result = LocalTime.ofSecondOfDay(avgWaitTime);
            LocalTime result2 = LocalTime.ofSecondOfDay(avgServiceTime);
            avgWaitTimeLabel.setText("" + result);
            if (avgServiceTime <= 300) {
                productivityLabel.setText("100%");
            } else {
                float ideal_average = 300;
                int percent = 100;
                float t = ideal_average / avgServiceTime;
                System.out.println(t);
                int time = (int) (t * percent);
                productivityLabel.setText("" + time + "%");
            }
            peopleAttentedTO.setText("" + count2);
            avgServiceTimeLabel.setText("" + result2);
            pool.releaseConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//flag

    @FXML
    void statsBackButtonPressed() {
        menuBar.setVisible(true);
        String selectedItem = loginCombo.getSelectionModel().getSelectedItem();
        for (int i = 0; i < paneList.size(); i++) {
            if(selectedItem.equals(paneList.get(i).getService().getServiceName()))
                doSlideInFromLeft(cardsStackPane, paneList.get(i));
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Help Menu">
    @FXML
    void checkForUpdatesPressed() {
        createAlert("Updates", "No new update available at the moment", "Application up to date");
    }
    @FXML
    void aboutPressed() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About");
        alert.setContentText("Queue Deck, LLC.");
        alert.showAndWait();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Login">
    @FXML
    public void loginButtonPressed() {
        loginActionToPerform();
    }
    @FXML
    void loignNodeKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginActionToPerform();
        }
    }
    @FXML
    void loginComboMouseClicked(MouseEvent event) {
        loginActionLabel.setText("");
    }
//</editor-fold>

    boolean flag1 = false;
    boolean flag2 = false;
    String pline, trans;
    
    @Override
    public void initialize(URL Url, ResourceBundle rb) {
        DAOInterface dao = new JPAClass();
        changeServiceMenu.getItems().clear();
        for(int i=0;i<servList.size();i++){
            loginCombo.getItems().add(i, servList.get(i).getServiceName());
            MenuItem mi = new MenuItem(servList.get(i).getServiceName());
            changeServiceMenu.getItems().add(i, mi);
            ControlView cv = new ControlView(servList.get(i));
            String sel = servList.get(i).getServiceName();
            paneList.add(cv);
            mi.setOnAction((t) -> {
                changeServicePerformAction(sel, cv);
            });
        }
        menuBar.setVisible(false);
        showNode(cardsStackPane, loginNode);
        
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        staffNoTextField.setText(prefs.get("Staff No", ""));

        Platform.runLater(() -> {
            containerPane.getScene().getWindow().setOnCloseRequest((t) -> {close();});
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        Connection con2 = pool.getConnection();
                        while (true) {
                            Platform.runLater(() -> {
                                LocalTime localTime = LocalTime.parse(changeStringFormat(String.valueOf(LocalTime.now()).substring(0, 2)) + ":" + String.valueOf(LocalTime.now()).substring(3, 5) + ":" + "00");
                                try {
                                    Statement stmt = con2.createStatement();
                                    servList = dao.listServices();
                                    for(int i = 0; i < servList.size(); i++){
                                        //Checking if service is locked
                                        if(servList.get(i).getUnlockTime() != null){
                                            LocalTime unlockTime = LocalTime.parse(servList.get(i).getUnlockTime());
                                            System.out.println("unlockt");
                                            if(unlockTime.equals(localTime)){
                                                paneList.get(i).lock.setSelected(false);
                                                paneList.get(i).lock.setText("Lock");
                                                stmt.executeUpdate("update services set locked = '0', unlock_time = null where s_no = '" + servList.get(i).getServiceNo() + "'");
                                                System.out.println("time unlock done");
                                            }else{
                                                paneList.get(i).lock.setSelected(true);
                                                paneList.get(i).lock.setText("Unlock");
                                            }
                                        }
                                        //Getting data updates
                                        if (loginCombo.getSelectionModel().getSelectedItem() != null) {
                                        if(loginCombo.getSelectionModel().getSelectedItem().equals(servList.get(i).getServiceName())){
                                            ResultSet rs5 = con2.prepareStatement("select sum(case when time_called IS NULL and tag = '" + servList.get(i).getServiceNo() + "' then 1 else 0 end) as count_num from tickets where t_date='" + local_date + "'").executeQuery();
                                            ResultSet rs5b = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = '" + servList.get(i).getServiceNo() + "' then 1 else 0 end) as count_num from transfer where t_date='" + local_date + "'").executeQuery();
                                            int ppline = 0;
                                            int pplineb = 0;
                                            while (rs5.next() && rs5b.next()) {
                                                ppline = rs5.getInt("count_num");
                                                pplineb = rs5b.getInt("count_num");
                                                if ("" + ppline == null) {
                                                    ppline = 0;
                                                    paneList.get(i).noInlineLabel.setText("" + ppline);
                                                }else if ("" + pplineb == null) pplineb = 0;
                                                 else if ("" + pplineb != null) ppline = (ppline + pplineb);
                                                paneList.get(i).noInlineLabel.setText("" + ppline);
                                                }
                                            if(showNotifcationMenuItem.isSelected()){
                                                int notifCount = ppline + pplineb;
                                                if (notifCount > 0) flag1 = true;
                                                if (notifCount == 0 && flag1) flag2 = true;
                                                if (flag1 && flag2 && notifCount > 0) {
                                                    createAlertWhenThereIsATkt();
                                                    flag1 = false;
                                                    flag2 = false;
                                                }
                                            }
                                                ResultSet rst2 = con2.prepareStatement("select sum(case when time_called IS NULL and trans_to = '" + servList.get(i).getServiceNo() + "' then 1 else 0 end) as count_num_trans from transfer where t_date='" + local_date + "'").executeQuery();
                                                while (rst2.next()) {
                                                    String noTrans = rst2.getString("count_num_trans");
                                                    paneList.get(i).transferCounterLabel.setText(noTrans);
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }catch (SQLException ex) {System.out.println(ex);}
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        //pool.releaseConnection(con2);
                        //return null;
                    }
                };
                Thread t = new Thread(task);
                t.setDaemon(true);
                t.start();

        });
    }
}
