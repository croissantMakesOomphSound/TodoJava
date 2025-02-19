package com.example.taskmanager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import org.bson.Document;

public class newuserController {
    @FXML
    private Stage nustage;
    @FXML
    private TextField name;
    @FXML
    private TextField Username;
    @FXML
    private TextField Email;
    @FXML
    private TextField phoneno;

    @FXML
    private ChoiceBox<String> question1;
    @FXML
    private TextField answer1;
    @FXML
    private ChoiceBox<String> question2;
    @FXML
    private TextField answer2;

    @FXML
    private TextField newPassword;
    @FXML
    private TextField confirmPassword;
    @FXML
    private PasswordField newpass1;
    @FXML
    private PasswordField confirmpass1;

    @FXML
    private Button SubmitButton1;
    @FXML
    private Button SubmitButton2;
    @FXML
    private Button SubmitButton3;

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
    @FXML
    private AnchorPane anchorpane4;

    private boolean condition1=false;
    private boolean condition2=false;

    mongo m=new mongo();
    Document query=new Document();

    @FXML
    public void initialize(){
        anchorpane2.setVisible(false);
        anchorpane3.setVisible(false);
        anchorpane4.setVisible(false);

    }
    @FXML
    public void setstage(Stage stage){
        nustage=stage;
    }
    @FXML
    private void SubmitButton1(MouseEvent event) throws IOException {
        if(!Username.getText().isEmpty() || !Email.getText().isEmpty() ||!name.getText().isEmpty()||!phoneno.getText().isEmpty()) {
               query.append("name",name.getText())
                    .append("username",Username.getText())
                    .append("email",Email.getText())
                    .append("phoneno",phoneno.getText());
            if(m.read("users",query)==null) {
                condition1 = true;
            }
            else{
                System.out.print("already exists");
            }

            if (condition1) {
                anchorpane1.setVisible(false);
                anchorpane2.setVisible(true);
                setanchorpane2();
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
    private void setanchorpane2()   {
        String[] choices = {"What is your middle name?"
                , "What is the name of your High School?"
                , "What is your pet's name?"
                , "What is the model of your first car?"
                , "What is your favourite artist?"};
        ObservableList<String> choice = FXCollections.observableArrayList(choices);
        question1.setItems(choice);
        question2.setItems(choice);
    }
    @FXML
    private void SubmitButton2(MouseEvent event) throws IOException{
        if(!answer1.getText().isEmpty() && !answer2.getText().isEmpty()){
            if(!question1.getSelectionModel().getSelectedItem().equals(question2.getSelectionModel().getSelectedItem())){
                Document securityques = new Document("question1", question1.getSelectionModel().getSelectedItem())
                        .append("answer1", answer1.getText())
                        .append("question2", question2.getSelectionModel().getSelectedItem())
                        .append("answer2", answer2.getText());
                query.append("Security Questions", securityques);
                condition2 = true;
            }
            else{
                condition2=false;
                System.out.println("both fields are matching.");
            }
            if (condition2) {
                anchorpane1.setVisible(false);
                anchorpane2.setVisible(false);
                anchorpane3.setVisible(true);
                SubmitButton2.setVisible(false);
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
    private void SubmitButton3(MouseEvent event) throws IOException {
        if(!newpass1.getText().isEmpty() || !confirmpass1.getText().isEmpty()){
            if(newpass1.getText().equals(confirmpass1.getText()) || newPassword.getText().equals(confirmPassword.getText())){
                query.append("password",newpass1.getText());
                boolean flag=m.create("users",query);
                if(flag){
                    anchorpane3.setVisible(false);
                    anchorpane4.setVisible(true);
                }
                else{
                    System.out.print("Error in writing data");
                }
            }
            else{
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Fieldnotmatching.fxml"));
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
        if(newpass1.isVisible() && !newPassword.isVisible()){
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
        if(confirmpass1.isVisible() && !confirmPassword.isVisible()){
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
        controller.setstage(stage);
        stage.setScene(scene);
        nustage.close();
        stage.show();
    }


}


