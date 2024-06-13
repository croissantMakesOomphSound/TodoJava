package com.example.taskmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class setreminderController {
    @FXML
    private Stage setreminderstage;
    @FXML
    public void setstage(Stage stage){
        setreminderstage=stage;
    }
    @FXML
    private void HomeButton(MouseEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Taskmanager.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller = fxmlLoader.getController();
        controller.initialize();
        controller.setstage(stage);
        stage.setScene(scene);
        setreminderstage.close();
        stage.show();
    }
    @FXML
    private void viewprofile(MouseEvent event) throws IOException{
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewprofile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewprofileController controller = fxmlLoader.getController();
        controller.setstage(stage);
        stage.setScene(scene);
        setreminderstage.close();
        stage.show();

    }
}
