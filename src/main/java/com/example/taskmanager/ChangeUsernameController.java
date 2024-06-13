package com.example.taskmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeUsernameController {
    private Stage ChangeUserstage;
    private Label existingusername;
    private TextField newusername;
    private TextField confirmusername;

    public void setstage(Stage stage){
        ChangeUserstage=stage;
    }
    public void initialize(){

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
        ChangeUserstage.close();
        stage.show();
    }
    @FXML
    private void ChangeButton(MouseEvent event){

    }
    @FXML
    private void UndoButton(MouseEvent event){

    }
    @FXML
    private void BacktoProfileButton(MouseEvent event) throws IOException{
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewprofile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewprofileController controller = fxmlLoader.getController();
        controller.initialize();
        controller.setstage(stage);
        stage.setScene(scene);
        ChangeUserstage.close();
        stage.show();
    }
}
