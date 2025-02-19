package com.example.taskmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;


public class test extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //RecommendationClass recommendationClass=new RecommendationClass();
        //recommendationClass.initialize("google","amazon",0.3);
       /* FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("webview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        webviewController controller = fxmlLoader.getController();
        controller.intialize("https://www.google.co.in/search?query=","banana",0.50);
        stage.setScene(scene);
        stage.show();*/
        mongo m=new mongo();
        Document query = new Document("username", "admin").append("password", "1234");
        Document  document=m.read("users", query);
        if(document!=null){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Taskmanager.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller = fxmlLoader.getController();
        controller.setDocument(document);
        controller.setstage(stage);
        controller.initialize();
        stage.setScene(scene);
        stage.show();}
        else{
            System.out.println("doc not found");
        }
        /*Document notifications =new Document();
        ObjectId objectId = new ObjectId("666e7712dcba0f1a2d123360");
        notifications.append("_id", objectId).append("username","admin");


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewreminder.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewreminderController  controller = fxmlLoader.getController();
        controller.setstage(stage);
        controller.setDocument(notifications);
        controller.initialize();
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }
}