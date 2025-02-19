package com.example.taskmanager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class setreminderController {
    Document document = new Document();
    static int i=0;
    @FXML
    private Stage setreminderstage;

    @FXML
    private ChoiceBox<String> setactivity;

    @FXML
    private TextField Hours;

    @FXML
    private TextField minutes;

    @FXML
    private TextField usermessage;
    @FXML
    private DatePicker date;
    @FXML
    private Label message;

    @FXML
    private ChoiceBox<String> setcalactivity;

    @FXML
    private TextField calHours;

    @FXML
    private TextField calminutes;

    @FXML
    private TextField calusermessage;

    @FXML
    private DatePicker caldate;

    @FXML
    private Label calmessage;

    mongo m = new mongo();
    Document notifications =new Document();

    notificationhandler n=new notificationhandler();
    private static boolean isupdated=false;

    @FXML
    public void setstage(Stage stage) {
        setreminderstage = stage;
    }

    @FXML
    public void setDocument(Document document) {
        this.document = document;
    }

    public void initialize() {
        //to-do
        Document query = new Document("_id", document.get("_id"))
                             .append("username", document.get("username"));
        Document result = m.read("to-do", query);


        if (result != null) {
            ArrayList<Document> resultarray=(ArrayList<Document>) result.get("todo");
            ObservableList<String> item=FXCollections.observableArrayList();
            for(Document i:resultarray){
                item.add(i.get("item").toString()+" -- \t"+i.get("link").toString());
            }
            setactivity.setItems(item);
        }

        date.setValue(LocalDate.now());
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        Hours.setText(String.valueOf(currentHour));
        int currentMinute = currentTime.getMinute();
        minutes.setText(String.valueOf(currentMinute));

        notifications.append("_id",document.get("_id"))
                     .append("username",document.get("username"));
        //calender
        Document calquery = new Document("User_id", document.get("_id"))
                            .append("username", document.get("username"));
        List<Document> calresult = m.readALL("calender", calquery);
        System.out.println("\n\n\n\n\n"+calresult);
        if (!calresult.isEmpty()) {
            ArrayList<String> calresultarray;
            ObservableList<String> calitems=FXCollections.observableArrayList();
            for(Document i:calresult) {
                ArrayList<Document> caltasks = i.get("tasks", ArrayList.class);
                for(Document j:caltasks) {
                   if(j.get("completed").toString().equals("No")) {
                    String choice = "date/time:" + i.get("date") + "/" + j.get("time")
                            + "|| task: " + j.get("task")
                            + "|| notes: " + j.get("notes")
                            + "|| category: " + j.get("category");
                    System.out.println(choice);
                    calitems.add(choice);
                     }
                 }
            }
            setcalactivity.setItems(calitems);
        }

        caldate.setValue(LocalDate.now());
        calHours.setText(String.valueOf(currentHour));
        calminutes.setText(String.valueOf(currentMinute));


    }

    @FXML
    private void SubmitButtontodo(MouseEvent event) {
        String hours=Hours.getText();
        String mins=minutes.getText();
        LocalDate todaydate=date.getValue();
        String activity=setactivity.getSelectionModel().getSelectedItem();
        String Message=usermessage.getText();
        if(Message==null){

            Message="";
        }

        Document remindertime=new Document();
        Document notification=new Document();

        remindertime.append("hours",hours)
                    .append("minutes", mins);

        notification.append("type", "to-do")
                    .append("activity", activity)
                    .append("message",Message)
                    .append("time", remindertime)
                    .append("date",todaydate)
                    .append("status","ongoing");

        Document search =m.read("notifications",notifications);
        ArrayList<Document> notificationList=new ArrayList<Document>();
        if(search !=null) {
            List<Document> existingNotifications = (List<Document>) search.get("notification");
            ArrayList<Document> oldArrayList = new ArrayList<>(existingNotifications);
            oldArrayList.add(notification);
            System.out.println("\n\n"+notification+"\n\n\n\n");
            m.update("notifications",notifications,"notification",oldArrayList);
            message.setText("notification is set.");
            n.checkupdated(true);

        }
        else{
              notificationList.add(notification);
              notifications.append("notification",notificationList);
              m.create("notifications",notifications);
              message.setText("notification is set.");
              n.checkupdated(true);
        }
           i++;
    }

    @FXML
    private void SubmitButtoncalender(MouseEvent event) {
        String hours=calHours.getText();
        String mins=calminutes.getText();
        LocalDate todaydate=caldate.getValue();
        String activity=setcalactivity.getSelectionModel().getSelectedItem();
        String Message=calusermessage.getText();
        if(Message==null){

            Message="";
        }

        Document remindertime=new Document();
        Document notification=new Document();

        remindertime.append("hours",hours)
                    .append("minutes", mins);

        notification.append("type", "to-do")
                .append("activity", activity)
                .append("message",Message)
                .append("time", remindertime)
                .append("date",todaydate)
                .append("status","ongoing");

        Document search =m.read("notifications",notifications);
        ArrayList<Document> notificationList=new ArrayList<Document>();
        if(search !=null) {
            List<Document> existingNotifications = (List<Document>) search.get("notification");
            ArrayList<Document> oldArrayList = new ArrayList<>(existingNotifications);
            oldArrayList.add(notification);
            System.out.println("\n\n"+oldArrayList+"\n\n\n\n");
            m.update("notifications",notifications,"notification",oldArrayList);
            calmessage.setText("notification is set.");
            n.checkupdated(true);

        }
        else{
            notificationList.add(notification);
            notifications.append("notification",notificationList);
            m.create("notifications",notifications);
            calmessage.setText("notification is set.");
            n.checkupdated(true);
        }
        i++;
    }


    @FXML
    private void HomeButton(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Taskmanager.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller = fxmlLoader.getController();
        controller.setstage(stage);
        controller.setDocument(document);
        controller.initialize();
        stage.setScene(scene);
        setreminderstage.close();
        stage.show();
    }

    @FXML
    private void viewprofile(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewprofile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewprofileController controller = fxmlLoader.getController();
        controller.setstage(stage);
        controller.setDocument(document);
        controller.initialize();
        stage.setScene(scene);
        setreminderstage.close();
        stage.show();
    }

    @FXML
    private void viewreminder(MouseEvent event) throws IOException{
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewreminder.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewreminderController  controller = fxmlLoader.getController();
        controller.setstage(stage);
        controller.setDocument(document);
        controller.initialize();
        stage.setScene(scene);
        setreminderstage.close();
        stage.show();

    }
}
