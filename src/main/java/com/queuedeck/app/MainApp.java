package com.queuedeck.app;

import com.queuedeck.controllers.FXMLController;
import static com.queuedeck.controllers.FXMLController.pool;
import com.queuedeck.models.ControlView;
import com.queuedeck.models.DAOInterface;
import com.queuedeck.models.JPAClass;
import com.queuedeck.models.Service;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/QDMangerScene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Style-Default.css");

        stage.setTitle("Queue Deck Manager");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("/img/ficon.jpeg").toString()));

//        Thread thread = new Thread(() -> {
//            Runnable updater = () -> {
//                DAOInterface d = new JPAClass();
//                List<Service> servList = d.listServices();
//                List<ControlView> paneList = d.listControlViewForAllServices();
//                LocalTime localTime = LocalTime.parse(String.valueOf(LocalTime.now()).substring(0, 2) + ":" + String.valueOf(LocalTime.now()).substring(3, 5) + ":" + "00");
//                try {
//                    Connection con2 = pool.getConnection();
//                    Statement stmt = con2.createStatement();
//                    servList = d.listServices();
//                    for (int i = 0; i < servList.size(); i++) {
//                        if (servList.get(i).getUnlockTime() != null) {
//                            LocalTime unlockTime = LocalTime.parse(servList.get(i).getUnlockTime());
//                            System.out.println("unlockt in main");
//                            if (unlockTime.equals(localTime)) {
//                                paneList.get(i).lock.setSelected(false);
//                                paneList.get(i).lock.setText("Lock");
//                                stmt.executeUpdate("update services set locked = '0', unlock_time = null where s_no = '" + servList.get(i).getServiceNo() + "'");
//                                System.out.println("time unlock done");
//                            } else {
//                                paneList.get(i).lock.setSelected(false);
//                                paneList.get(i).lock.setText("Lock");
//                            }
//                        }
//                    }
//                    pool.releaseConnection(con2);
//                } catch (SQLException s) {
//                    System.out.println(s);
//                }
//            };
//            Platform.runLater(updater);
//            while (true) {
//                try {
//                    
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stage closing");
        super.stop();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
