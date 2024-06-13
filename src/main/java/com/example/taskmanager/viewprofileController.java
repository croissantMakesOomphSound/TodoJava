package com.example.taskmanager;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class viewprofileController {
    private Stage viewprofilestage;
    private TextField textfield1;
    private TextField textfield2;
    private TextField textfield3;

    public void setstage(Stage stage){
        viewprofilestage=stage;
    }
    public void initialize(){

    }
    @FXML
    private void setName(ActionEvent event){
    //load data
        textfield1.getText();
}
    @FXML
    private void setUserName(ActionEvent event){
    //load data
        textfield2.getText();
    }
    @FXML
    private void setEmail(ActionEvent event){
    //load data
        textfield3.getText();
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
        viewprofilestage.close();
        stage.show();
    }

    @FXML
    private void changeemail(MouseEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChangeEmail.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ChangeEmailController controller = fxmlLoader.getController();
        controller.setstage(stage);
        stage.setScene(scene);
        viewprofilestage.close();
        stage.show();
    }
    @FXML
    private void changeusername(MouseEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChangeUsername.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ChangeUsernameController controller = fxmlLoader.getController();
        controller.setstage(stage);
        stage.setScene(scene);
        viewprofilestage.close();
        stage.show();
    }


}
