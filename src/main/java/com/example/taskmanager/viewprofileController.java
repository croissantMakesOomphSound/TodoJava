package com.example.taskmanager;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import org.bson.Document;

public class viewprofileController {
    @FXML
    private Stage viewprofilestage;
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label phoneno;
    @FXML
    private TextField inputfield1;
    @FXML
    private TextField inputfield2;
    @FXML
    private TextField inputfield3;
    @FXML
    private TextField inputfield4;

    mongo m=new mongo();
    Document document=new Document();
    Document olddoc=new Document();
    public void setstage(Stage stage){
        viewprofilestage=stage;
    }
    public void setDocument(Document document1){
        document=document1;

    }
    @FXML
    public void initialize(){
        if(document!=null) {
            name.setText(document.getString("name"));
            username.setText(document.getString("username"));
            email.setText(document.getString("email"));
            phoneno.setText(document.getString("phoneno"));
        }
        else{
            System.out.println("document is nuull");
        }
        Document query=new Document("_id",document.get("_id")).append("username",document.get("username"));
        olddoc=m.read("user preferences",query);
        System.out.println("oldodc"+olddoc);
        if(olddoc!=null){
            inputfield1.setText(olddoc.get("filepath").toString());
            inputfield2.setText(olddoc.get("ide").toString());
            inputfield3.setText(olddoc.get("browser").toString());
            inputfield4.setText(olddoc.get("shopping site").toString());
        }
        else{
            inputfield1.setText("");
            inputfield2.setText("");
            inputfield3.setText("");
            inputfield4.setText("");
        }
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
        viewprofilestage.close();
        stage.show();
    }

    @FXML
    private void changeemail(MouseEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChangeEmail.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ChangeEmailController controller = fxmlLoader.getController();
        controller.setDocument(document);
        controller.setstage(stage);
        controller.initialize();
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
        controller.setDocument(document);
        controller.setstage(stage);
        controller.initialize();
        stage.setScene(scene);
        viewprofilestage.close();
        stage.show();
    }
    @FXML
    private void changephoneno(MouseEvent event) throws IOException {
        System.out.println("changeusername is pressed");
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChangePhoneno.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ChangePhonenoController controller = fxmlLoader.getController();
        controller.setDocument(document);
        controller.setstage(stage);
        controller.initialize();
        stage.setScene(scene);
        viewprofilestage.close();
        stage.show();
    }
    @FXML
    private void changepassword(MouseEvent event) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ChangePassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ChangePasswordController controller = fxmlLoader.getController();
        controller.setDocument(document);
        controller.setstage(stage);
        controller.initialize();
        stage.setScene(scene);
        viewprofilestage.close();
        stage.show();
    }
    @FXML
    private void file(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(viewprofilestage);
        if (selectedFile != null) {
            inputfield1.setText(selectedFile.getAbsolutePath());
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected");
        }
    }
    @FXML
    private void file1(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(viewprofilestage);
        if (selectedFile != null) {
            inputfield2.setText(selectedFile.getAbsolutePath());
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected");
        }
    }

    @FXML
    private  void  confirm(MouseEvent event){
        String input1= inputfield1.getText();
        String input2= inputfield2.getText();
        String input3= inputfield3.getText();
        String input4= inputfield4.getText();
        Document newdoc=new Document("_id",document.get("_id"))
                .append("username",document.get("username"))
                .append("filepath",input1)
                .append("ide",input2)
                .append("browser",input3)
                .append("shopping site",input4);
        m.create("user preferences",newdoc);
        m.update("user preferences",olddoc,newdoc);
    }


}
