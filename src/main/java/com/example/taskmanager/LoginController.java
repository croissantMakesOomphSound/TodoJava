package com.example.taskmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Stage primarystage;
    @FXML
    private Scene primaryscene;
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;
    @FXML
    private PasswordField Password1;

    private String UserName;
    private String password;
    private boolean condition1=false;

    @FXML
    public void setstage(Stage stage){
        primarystage=stage;
    }

    @FXML
    private void SubmitButton(ActionEvent event){
        UserName =Username.getText();
        if(Password1.isVisible()==true) {
            password = Password1.getText();
        }
        else {
            password = Password.getText();
        }
        System.out.println(UserName);
        System.out.println(password);
        condition1=true;
        if(condition1==true) {
           try {
               starttaskmanager();
           }
           catch(IOException error){

           }
        }
    }
    @FXML
    private void starttaskmanager() throws IOException {
            Stage stage =new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Taskmanager.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            HelloController controller = fxmlLoader.getController();
            controller.initialize();
            primarystage.close();
            controller.setstage(stage);
            stage.setScene(scene);
            stage.show();
    }
    @FXML
    private void Forgotpassword(MouseEvent event) throws IOException{
        Stage stage =new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("forgotpassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        forgotpasswordcontroller controller = fxmlLoader.getController();
        controller.initialize();
        primarystage.close();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void newuser(MouseEvent event) throws IOException{
        Stage stage =new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newuser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        newuserController controller = fxmlLoader.getController();
        controller.initialize();
        primarystage.close();
        stage.setScene(scene);

        stage.show();
    }
    @FXML
    private void showButton(MouseEvent event){
        UserName =Username.getText();
        password =Password1.getText();
               if(Password1.isVisible()){
                   Password1.setVisible(false);
                   Password.setVisible(true);
                   Password.setText(password);
                   password=Password.getText();
               }
               else {
                   Password1.setVisible(true);
                   Password.setVisible(false);
                   Password1.setText(password);
               }

    }
}
