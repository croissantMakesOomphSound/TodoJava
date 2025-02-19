package com.example.taskmanager;

import com.mongodb.MongoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import org.bson.Document;



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
    mongo m = new mongo();
    Document document=new Document();
    @FXML
    public void setstage(Stage stage){
        primarystage=stage;
    }

    @FXML
    private void SubmitButton(ActionEvent event)throws IOException {
        UserName = Username.getText();
        if (Password1.isVisible()) {
            password = Password1.getText();
        } else {
            password = Password.getText();
        }
        Username.toString().trim();
        password.trim();
        Document query = new Document("username", UserName).append("password", password);
        document=m.read("users", query);
        if (document!= null) {
            try {
                starttaskmanager();
            }catch (IOException error) {

            }catch (MongoException e) {

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
    @FXML
    private void starttaskmanager() throws IOException {
            Stage stage =new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Taskmanager.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            HelloController controller = fxmlLoader.getController();
            controller.setDocument(document);
            controller.setstage(stage);
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
        controller.setstage(stage);
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
        controller.setstage(stage);
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
