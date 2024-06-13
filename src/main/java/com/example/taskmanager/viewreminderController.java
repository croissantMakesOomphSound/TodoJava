package com.example.taskmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class viewreminderController {
    @FXML
    private Stage viewreminderstage;
    @FXML
    public void setstage(Stage stage){
        viewreminderstage=stage;
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
        viewreminderstage.close();
        stage.show();
    }
    @FXML
    private void addButton(MouseEvent event) throws IOException{
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("setreminder.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setreminderController controller = fxmlLoader.getController();
        controller.setstage(stage);
        stage.setScene(scene);
        viewreminderstage.close();
        stage.show();
    }
}
