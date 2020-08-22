package com.queuedeck.app;

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
        Image icon1 = new Image(this.getClass().getResource("/img/icon/icon1.png").toString());
        Image icon2 = new Image(this.getClass().getResource("/img/icon/icon2.png").toString());
        Image icon3 = new Image(this.getClass().getResource("/img/icon/icon3.png").toString());
        Image icon4 = new Image(this.getClass().getResource("/img/icon/icon4.png").toString());
        Image icon5 = new Image(this.getClass().getResource("/img/icon/icon5.png").toString());
        Image icon6 = new Image(this.getClass().getResource("/img/icon/icon6.png").toString());
        Image icon7 = new Image(this.getClass().getResource("/img/icon/icon7.png").toString());
        
        stage.getIcons().addAll(icon1,icon2,icon3,icon4,icon5,icon6,icon7);
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
