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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;
import  com.mongodb.client.MongoCollection;
import java.io.IOException;

public class ChangePasswordController {
    @FXML
    private Stage ChangePasswordStage;
    @FXML
    private Label existingpassword;
    @FXML
    private Label display;

    private String olduserpassword;
    @FXML
    private TextField newpassword;
    @FXML
    private TextField confirmpassword;
    @FXML
    private PasswordField newpass1;
    @FXML
    private PasswordField confirmpass1;
    mongo m=new mongo();
    Document document=new Document();


    public void setstage(Stage stage){
        ChangePasswordStage =stage;
    }
    public void setDocument(Document document1){
        document=document1;
        System.out.println("document is set"+document.toJson());
    }
    public void initialize(){
        olduserpassword = document.getString("password");
        newpassword.setVisible(false);
        confirmpassword.setVisible(false);
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
        ChangePasswordStage.close();
        stage.show();
    }
    @FXML
    private void ChangeButton(MouseEvent event)throws IOException, MongoException {
        if(newpassword.getText().equals(confirmpassword.getText()) && !newpassword.getText().isEmpty()) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Commitnewfield.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            CommitnewfieldController controller = fxmlLoader.getController();
            controller.setstage(stage);
            controller.getfield("password"," ");
            stage.setScene(scene);
            stage.showAndWait();
            if (controller.check()) {
                System.out.println("flag:true");
                if(m.update("users", document, "password", newpassword.getText())){
                    display.setText("password is changed to:newpassword");
                    existingpassword.setText(newpassword.getText());
                    document.put("password", newpassword.getText());
                    MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
                    MongoDatabase database = mongoClient.getDatabase("taskmanager");
                    MongoCollection<Document> collection = database.getCollection("to-do");
                    Document query = new Document("_id", document.get("_id"));
                    Document tododocument = collection.find(query).first();
                    m.update("to-do",tododocument,"password", newpassword.getText());
                }
                else{
                    display.setText("password change is failed");
                }
            }
            else {
                System.out.println("flag:false");
            }
        }
        else if (newpassword.getText()==null || newpassword.getText().isEmpty()) {
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
        if(m.update("users", document, "password", olduserpassword) && olduserpassword !=null){
            display.setText("password is changed to:olduserpassword");
            existingpassword.setText(olduserpassword);
            document.put("password", olduserpassword);
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
        ChangePasswordStage.close();
        stage.show();
    }
    @FXML
    private void show1Button(MouseEvent event){
        if(newpass1.isVisible() && !newpassword.isVisible()){
            newpass1.setVisible(false);
            newpassword.setText(String.valueOf(newpass1.getText()));
            newpassword.setVisible(true);
        }
        else if(!newpass1.isVisible() && newpassword.isVisible()) {
            newpass1.setVisible(true);
            newpass1.setText(String.valueOf(newpassword.getText()));
            newpassword.setVisible(false);
        }
    }
    @FXML
    private void show2Button(MouseEvent event){
        if(confirmpass1.isVisible() && !confirmpassword.isVisible()){
            confirmpass1.setVisible(false);
            confirmpassword.setText(String.valueOf(confirmpass1.getText()));
            confirmpassword.setVisible(true);
        }
        else if(!confirmpass1.isVisible() && confirmpassword.isVisible()){
            confirmpassword.setVisible(false);
            confirmpass1.setText(String.valueOf(confirmpassword.getText()));
            confirmpass1.setVisible(true);
        }
    }
}
