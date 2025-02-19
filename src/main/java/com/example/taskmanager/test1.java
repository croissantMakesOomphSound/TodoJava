package com.example.taskmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static javafx.application.Application.launch;

public class test1 extends HelloApplication{

@Override
   public void  start(Stage stage) throws IOException {
      Document notifications =new Document();
      ObjectId objectId = new ObjectId("666e7712dcba0f1a2d123360");
      notifications.append("_id", objectId).append("username","admin");


      FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewreminder.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      viewreminderController  controller = fxmlLoader.getController();
      controller.setstage(stage);
      controller.setDocument(notifications);
      controller.initialize();
      stage.setScene(scene);
      stage.show();

   }
   public static void main(String[] args) {
      launch();

      /*  mongo m = new mongo();
        Document notifications =new Document();
        ArrayList<Document> notificationArrayList=new ArrayList<Document>();
        Document remindertime=new Document();


        Document notification1 =new Document();
        Document remindertime1=new Document();
        ObjectId objectId = new ObjectId("666e7712dcba0f1a2d123360");


        notifications.append("_id", objectId)
                     .append("username","admin");

        Document notification=new Document();

        remindertime.append("hours","19")
                    .append("minutes", "15");


        notification.append("type", "to-do")
                    .append("activity", "buy gunsssssssssss")
                    .append("time", remindertime)
                    .append("date","17/5/24")
                    .append("status","ongoing");
        notificationArrayList.add(notification);
        remindertime1.append("hours","19")
                .append("minutes", "16");


        notification1.append("type", "to-do")
                .append("activity", "buy grenadessssssss")
                .append("time", remindertime1)
                .append("date","17/5/24")
                .append("status","ongoing");
        notificationArrayList.add(notification1);
        Document search =m.read("notifications",notifications);

        ArrayList<Document> oldArrayList=new ArrayList<Document>();
        Document check=m.read("notifications",notifications);
        List<Document> notificationList = (List<Document>) check.get("notification");
            oldArrayList.addAll(notificationList);
            oldArrayList.addAll(notificationArrayList);
        m.update("notifications",notifications,"notification",oldArrayList); */ }
}
