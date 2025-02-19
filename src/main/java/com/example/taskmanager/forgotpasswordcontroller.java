package com.example.taskmanager;

import com.mongodb.MongoException;
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
import org.bson.Document;
import java.util.Random;

public class forgotpasswordcontroller {
    @FXML
    private Stage forgetpassstage;
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
    @FXML
    private AnchorPane anchorpane4;

    private boolean condition1=false;
    private final boolean condition2=false;

    mongo m = new mongo();
    Random r=new Random();
    Document securityquestions=new Document();
    Document document=new Document();
    private final int choice=r.nextInt(2);
    @FXML
    public void initialize(){
        anchorpane2.setVisible(false);
        anchorpane3.setVisible(false);
        anchorpane4.setVisible(false);
    }
    public void setstage(Stage stage){
        forgetpassstage=stage;
    }
    @FXML
    private void confirmButton(ActionEvent event) throws IOException{
        if(!Username.getText().isEmpty() || !Email.getText().isEmpty()) {
            Document query=new Document("username",Username.getText()).append("email",Email.getText());
            document=m.read("users",query);
            securityquestions=document.get("Security Questions", Document.class);
            if(document!=null){
                try{
                    anchorpane2.setVisible(true);
                    Confirm.setVisible(false);
                    if(choice==0){
                        question.setText(securityquestions.getString("question1"));
                    }
                    else{
                        question.setText(securityquestions.getString("question2"));
                    }
                }
                catch(MongoException e){

                }
            }
            else{
                Stage stage =new Stage();
                FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("Fieldnotmatching.fxml"));
                Scene scene= new Scene(fxmlLoader.load());
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
    private void submitButton1(ActionEvent event) throws IOException{

        if(!answer.getText().isEmpty()){
            System.out.println(securityquestions.getString("answer"+choice));
            System.out.println(answer.getText().equals(securityquestions.getString("answer"+(choice+1))));
            if(answer.getText().equals(securityquestions.getString("answer"+(choice+1)))){
                condition1=true;
            }
            else{
                Stage stage =new Stage();
                FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("Fieldnotmatching.fxml"));
                Scene scene= new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
            if(condition1=true){
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
                boolean flag=m.update("users",document,"password",newpass1.getText());
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
        forgetpassstage.close();
        stage.show();
    }
    @FXML
    private void newuser(MouseEvent event) throws IOException{
        Stage stage =new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newuser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        newuserController controller = fxmlLoader.getController();
        controller.initialize();
        controller.setstage(stage);
        forgetpassstage.close();
        stage.setScene(scene);

        stage.show();
    }

}
