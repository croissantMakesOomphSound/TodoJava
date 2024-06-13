package com.example.taskmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class forgotpasswordcontroller {
    @FXML
    private TextField Username;
    @FXML
    private TextField Email;
    @FXML
    private Label question;
    @FXML
    private TextField answer;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField confirmPassword;
    @FXML
    private PasswordField newpass1;
    @FXML
    private PasswordField confirmpass1;

    @FXML
    private Button Confirm;
    @FXML
    private Button Submit1;
    @FXML
    private Button show1;
    @FXML
    private Button show2;

    @FXML
    private AnchorPane anchorpane1;
    @FXML
    private AnchorPane anchorpane2;
    @FXML
    private AnchorPane anchorpane3;

    private boolean condition1=false;
    private boolean condition2=false;

    @FXML
    public void initialize(){
        anchorpane2.setVisible(false);
        anchorpane3.setVisible(false);
    }
    @FXML
    private void confirmButton(ActionEvent event) throws IOException{
        if(!Username.getText().isEmpty() || !Email.getText().isEmpty()) {
            condition1 = true;
            if (condition1 == true) {
                anchorpane2.setVisible(true);
                Confirm.setVisible(false);
            }
        }
        else{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nothing.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void submitButton1(ActionEvent event) throws IOException{
        if(!answer.getText().isEmpty()){
             condition2 = true;
            if (condition2 == true) {
               anchorpane1.setVisible(false);
               anchorpane2.setVisible(false);
               Submit1.setVisible(false);
               anchorpane3.setVisible(true);
               newpass1.setVisible(true);
               newPassword.setVisible(false);
               confirmpass1.setVisible(true);
               confirmPassword.setVisible(false);
            }
        }
        else{
             Stage stage = new Stage();
             FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nothing.fxml"));
             Scene scene = new Scene(fxmlLoader.load());
             stage.setScene(scene);
             stage.show();
        }
    }
    @FXML
    private void submitButton2(MouseEvent event) throws IOException {
        if(!newpass1.getText().isEmpty() || !confirmpass1.getText().isEmpty()){
            if(newpass1.getText().equals(confirmpass1.getText()) || newPassword.getText().equals(confirmPassword.getText())){
                         System.out.println("ok");
            }
            else{
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("emailnotmatching.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
        }
        else{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("nothing.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }

    }
    @FXML
    private void show1Button(MouseEvent event){
        if(newpass1.isVisible()==true && newPassword.isVisible()==false){
            newpass1.setVisible(false);
            newPassword.setText(String.valueOf(newpass1.getText()));
            newPassword.setVisible(true);
        }
        else{
            newpass1.setVisible(true);
            newpass1.setText(String.valueOf(newPassword.getText()));
            newPassword.setVisible(false);
        }
    }
    @FXML
    private void show2Button(MouseEvent event){
              if(confirmpass1.isVisible()==true && confirmPassword.isVisible()==false){
                  confirmpass1.setVisible(false);
                  confirmPassword.setText(String.valueOf(confirmpass1.getText()));
                  confirmPassword.setVisible(true);
              }
              else{
                  confirmPassword.setVisible(false);
                  confirmpass1.setText(String.valueOf(confirmPassword.getText()));
                  confirmpass1.setVisible(true);
              }
    }
    @FXML
    private void Login(MouseEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void newuser(MouseEvent event) throws IOException{
        System.out.println("new user");
    }

}
