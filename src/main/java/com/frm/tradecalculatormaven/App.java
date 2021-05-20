package com.frm.tradecalculatormaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * JavaFX App
 * This is the starting point of the App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/calcView.fxml"));
        Parent root= loader.load();
        CalcViewController controller=loader.getController();
        Scene scene=new Scene(root);
        
        Image icon=new Image("/icons/calculator.png");
        stage.getIcons().add(icon);
        stage.setTitle("Trade Calculator v1.2");
        stage.setScene(scene);
        stage.show();
        controller.setStage(stage);
        // Close the App on window close request
        stage.setOnCloseRequest(e->{
            Platform.exit();
            System.exit(0);
        });
    }

   

    public static void main(String[] args) {
        launch();
    }

}