package com.example.taskmanager;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import org.bson.Document;
import org.controlsfx.control.Notifications;
import javafx.application.Platform;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class notificationhandler {
    Document ogdocument=new Document();
    //checks if new reminder is added
    public  void checkupdated( boolean isupdated){
        if (isupdated){

            notify1();
        }

    }


    public  void  createnotification(String username,String message){
        Notifications.create()
                .title("Reminder:")
                .text("Hi "+username+" remember to do:  "+message)
                .showInformation();
    }

    private void scheduleFutureNotification(long delay, String username, String message) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> Platform.runLater(() -> createnotification(username, message)), delay, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    public void notify1() {
        mongo m = new mongo();
        MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("taskmanager");
        MongoCollection<Document> collection = database.getCollection("notifications");
        FindIterable<Document> documents = collection.find();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);

        for (Document document : documents) {
            Object obj = document.get("notification");
            if (obj instanceof ArrayList) {
                ArrayList<Document> notificationArrayList = (ArrayList<Document>) obj;
            for (Document notification : notificationArrayList) {
                if (notification.get("status", String.class).toString().equals("ongoing")) {
                    Document time = notification.get("time", Document.class);
                    String hours = time.getString("hours");
                    String minutes = time.getString("minutes");
                    int hour = Integer.parseInt(hours);
                    int mins = Integer.parseInt(minutes);
                    LocalTime notificationtime = LocalTime.of(hour, mins);

                    String username = document.getString("username");
                    String activity = notification.getString("activity");
                    String message = notification.getString("message");
                    System.out.println("\n username:" + username + "\n time:" + hour + ":" + mins + " activity" + activity);
                    String printmessage = activity + " with the message " + message;

                    LocalTime now = LocalTime.now();
                    long initialDelaySeconds = now.until(notificationtime, ChronoUnit.SECONDS);
                    if (initialDelaySeconds > -59 &&initialDelaySeconds<10 ) {
                        try {
                            Platform.runLater(()->
                                    createnotification(username, printmessage));
                            viewreminderController v=new viewreminderController();
                            v.updatecompleted(notification,document);
                            System.out.println("v update completed");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (initialDelaySeconds<-60){
                        continue;
                    }
                    else{
                        scheduleFutureNotification(initialDelaySeconds, username, printmessage);
                       /* executorService.schedule(() ->
                                Platform.runLater(() ->
                                        createnotification(username, printmessage)
                                ), initialDelaySeconds-10, TimeUnit.SECONDS
                        );*/
                    }
                }
            }
        }}

        mongoClient.close();
        executorService.close();
    }
    private long calculateDelay(LocalTime notificationTime) {
        LocalTime now = LocalTime.now();
        long initialDelaySeconds = now.until(notificationTime, ChronoUnit.SECONDS);
        if (initialDelaySeconds < 0) {
            initialDelaySeconds += TimeUnit.DAYS.toSeconds(1);
        }
        return initialDelaySeconds;
    }

}

