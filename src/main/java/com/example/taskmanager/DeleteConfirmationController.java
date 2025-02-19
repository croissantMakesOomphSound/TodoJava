package com.example.taskmanager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.bson.Document;


public class DeleteConfirmationController {
    private static boolean flag=false;
    @FXML
    private Label label;
    @FXML
    private Stage stage;
    @FXML
    public void setstage(Stage stage){
        this.stage=stage;
    }

    public void initialize(String selection) {
        label.setText(selection);

    }
    public boolean checkflag(){
        return flag;
    }
    @FXML
    private void DeleteButton(ActionEvent event) {
        flag=true;
        stage.close();

    }
    @FXML
    private void CancelButton(ActionEvent event){
        flag=false;
        stage.close();
    }


}
