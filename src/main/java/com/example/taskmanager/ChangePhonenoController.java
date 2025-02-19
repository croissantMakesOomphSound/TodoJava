package com.example.taskmanager;

import com.mongodb.MongoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;

public class ChangePhonenoController {
    @FXML
    private Stage ChangeUserstage;
    @FXML
    private Label existingphoneno;
    @FXML
    private Label display;
    private String oldphoneno;
    @FXML
    private TextField newphoneno;
    @FXML
    private TextField confirmphoneno;
    mongo m=new mongo();
    Document document=new Document();


    public void setstage(Stage stage){
        ChangeUserstage=stage;
    }
    public void setDocument(Document document1){
        document=document1;
    }
    public void initialize(){
        System.out.println("intialized");
        existingphoneno.setText(document.getString("phoneno"));
        System.out.println(existingphoneno.getText());
        oldphoneno= existingphoneno.getText();
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
        stage.show();
    }
    @FXML
    private void ChangeButton(MouseEvent event)throws IOException, MongoException {
        if(newphoneno.getText().equals(confirmphoneno.getText()) && !newphoneno.getText().isEmpty()) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Commitnewfield.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            CommitnewfieldController controller = fxmlLoader.getController();
            controller.setstage(stage);
            controller.getfield("phoneno",newphoneno.getText());
            stage.setScene(scene);
            stage.showAndWait();
            if (controller.check()) {
                System.out.println("flag:true");
                if (m.update("users", document, "phoneno", newphoneno.getText())) {
                    display.setText("phoneno is changed to:" + newphoneno.getText());
                    existingphoneno.setText(newphoneno.getText());
                    document.put("phoneno",newphoneno.getText());

                } else {
                    display.setText("phoneno change is failed");
                }
            }
            else {
                System.out.println("flag:false");
            }
        }
        else if (newphoneno.getText()==null || newphoneno.getText().isEmpty()) {
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
        if(m.update("users", document, "phoneno", oldphoneno)){
            display.setText("phoneno is changed to:"+oldphoneno);
            existingphoneno.setText(oldphoneno);
            document.put("phoneno",oldphoneno);
        }
        else{
            display.setText("phoneno change is failed");
        }
    }
    @FXML
    private void BacktoProfileButton(MouseEvent event) throws IOException{
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
