package com.example.taskmanager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;
import  com.mongodb.client.MongoCollection;
import java.io.IOException;

public class ChangeUsernameController {
    @FXML
    private Stage ChangeUserstage;
    @FXML
    private Label existingusername;
    @FXML
    private Label display;
    private String oldusername;
    @FXML
    private TextField newusername;
    @FXML
    private TextField confirmusername;
    mongo m=new mongo();
    Document document=new Document();


    public void setstage(Stage stage){
        ChangeUserstage=stage;
    }
    public void setDocument(Document document1){
        document=document1;
        System.out.println("document is set"+document.toJson());
    }
    public void initialize(){
       existingusername.setText(document.getString("username"));
       System.out.println(existingusername.getText());
       oldusername=existingusername.getText();
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
        if(newusername.getText().equals(confirmusername.getText()) && !newusername.getText().isEmpty()) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Commitnewfield.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        CommitnewfieldController controller = fxmlLoader.getController();
        controller.setstage(stage);
        controller.getfield("username",newusername.getText());
        stage.setScene(scene);
        stage.showAndWait();
        if (controller.check()) {
            System.out.println("flag:true");
                      if(m.update("users", document, "username", newusername.getText())){
                          display.setText("username is changed to:"+newusername.getText());
                          existingusername.setText(newusername.getText());
                          document.put("username",newusername.getText());
                          MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
                          MongoDatabase database = mongoClient.getDatabase("taskmanager");
                          MongoCollection<Document> collection = database.getCollection("to-do");
                          Document query = new Document("_id", document.get("_id"));
                          Document tododocument = collection.find(query).first();
                          m.update("to-do",tododocument,"username", newusername.getText());
                      }
                      else{
                          display.setText("username change is failed");
                      }
            }
            else {
                 System.out.println("flag:false");
            }
        }
        else if (newusername.getText()==null || newusername.getText().isEmpty()) {
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
        if(m.update("users", document, "username", oldusername) && oldusername!=null){
            display.setText("username is changed to:"+oldusername);
            existingusername.setText(oldusername);
            document.put("username",oldusername);
        }
        else{
            display.setText("username change is failed");
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
