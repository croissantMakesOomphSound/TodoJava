package com.example.taskmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;

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
    @FXML
    private Label display;
    mongo m=new mongo();
    Document document=new Document();
    private String oldemail;

    public void setstage(Stage stage){
        ChangeUserstage=stage;
    }
    public void setDocument(Document document1){
        document=document1;
    }
    @FXML
    public void initialize(){
        existingemail.setText(document.getString("email"));
        System.out.println(existingemail.getText());
        oldemail=existingemail.getText();
    }

    @FXML
    private void HomeButton(MouseEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Taskmanager.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller = fxmlLoader.getController();
        controller.setDocument(document);
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
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Commitnewfield.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            CommitnewfieldController controller = fxmlLoader.getController();
            controller.setstage(stage);
            controller.getfield("email",nemail);
            stage.setScene(scene);
            stage.showAndWait();
            if (controller.check()) {
                System.out.println("flag:true");
                if(m.update("users", document, "email", newemail.getText())){
                    display.setText("email is changed to:"+newemail.getText());
                    existingemail.setText(newemail.getText());
                    document.put("email",newemail.getText());
                }
                else{
                    display.setText("email change is failed");
                }
            }
            else {
                System.out.println("flag:false");
            }

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
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Fieldnotmatching.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void UndoButton(MouseEvent event){
        if(m.update("users", document, "email", oldemail)){
            display.setText("email is changed to:"+oldemail);
            existingemail.setText(oldemail);
            document.put("email",oldemail);
        }
        else{
            display.setText("email change is failed");
        }

    }
    @FXML
    private void BacktoProfileButton(MouseEvent event) throws IOException,NullPointerException{
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewprofile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewprofileController controller = fxmlLoader.getController();
        controller.setDocument(document);
        controller.initialize();
        controller.setstage(stage);
        stage.setScene(scene);
        ChangeUserstage.close();
        stage.show();
    }
}
