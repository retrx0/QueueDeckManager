package com.queuedeck.app;

import com.queuedeck.controllers.FXMLController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
        stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.ico.png").toString()));
        
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
