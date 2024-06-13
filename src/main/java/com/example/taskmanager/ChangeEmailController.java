package com.example.taskmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeEmailController {
    @FXML
    private Stage ChangeUserstage;
    @FXML
    private Label existingemail;
    @FXML
    private TextField newemail;
    @FXML
    private TextField confirmemail;

    public void setstage(Stage stage){
        ChangeUserstage=stage;
    }
    public void initialize(){
                newemail.setText("");
                confirmemail.setText("");
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
        stage.showAndWait();
    }
    @FXML
    private void CommitButton(MouseEvent event) throws IOException {
        String nemail=newemail.getText();
        String cemail=confirmemail.getText();
        if (!nemail.isEmpty() && nemail.equals(cemail) ) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Commitnewemail.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            CommitnewemailController controller = fxmlLoader.getController();
            controller.setstage(stage);
            controller.getemail(nemail);
            stage.setScene(scene);
            stage.showAndWait();
        }
        else if (nemail==null || nemail.isEmpty()) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nothing.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
        else{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("emailnotmatching.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void UndoButton(MouseEvent event){
        String existingemail1=this.existingemail.getText();

    }
    @FXML
    private void BacktoProfileButton(MouseEvent event) throws IOException,NullPointerException{
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
