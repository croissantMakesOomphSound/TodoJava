package com.example.taskmanager;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.bson.Document;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class viewreminderController {
    public class MyData extends viewreminderController {
        private final StringProperty time = new SimpleStringProperty();
        private final StringProperty activity = new SimpleStringProperty();
        private final StringProperty message = new SimpleStringProperty();

        public MyData(String time, String activity, String message) {
            this.time.set(time);
            this.activity.set(activity);
            this.message.set(message);
        }

        // Getters and setters for properties
        public String getTime() {
            return time.get();
        }

        public StringProperty timeProperty() {
            return time;
        }

        public void setTime(String time) {
            this.time.set(time);
        }

        public String getActivity() {
            return activity.get();
        }

        public StringProperty activityProperty() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity.set(activity);
        }

        public String getMessage() {
            return message.get();
        }

        public StringProperty messageProperty() {
            return message;
        }

        public void setMessage(String message) {
            this.message.set(message);
        }
    }


    @FXML
    private Stage viewreminderstage;

    //ongoing
    @FXML
    private TableView<MyData> OngoingTable;

    @FXML
    private TableColumn<MyData, String> ongoingtime;

    @FXML
    private TableColumn<MyData, String> ongoingtask;

    @FXML
    private TableColumn<MyData, String> ongoingmessage;

    private ObservableList<MyData> ongoingData = FXCollections.observableArrayList();

    //completed
    @FXML
    private TableView<MyData> completedTable;

    @FXML
    private TableColumn<MyData, String> completedtime;

    @FXML
    private TableColumn<MyData, String> completedtask;

    @FXML
    private TableColumn<MyData, String> completedmessage;

    private ObservableList<MyData> completedData = FXCollections.observableArrayList();
    mongo m = new mongo();
    HelloController helloController=new HelloController();
    private Document document = new Document();
    private Document notifications = new Document();
    private Document check=new Document();

    notificationhandler n=new notificationhandler();
    private  boolean isupdated=false;

    @FXML
    public void setstage(Stage stage) {
        viewreminderstage = stage;
    }

    @FXML
    public void setDocument(Document document) {
        this.document = document;
    }

    public void initialize(){
        ongoingtime.setCellValueFactory(new PropertyValueFactory<>("time"));
        ongoingtask.setCellValueFactory(new PropertyValueFactory<>("activity"));
        ongoingmessage.setCellValueFactory(new PropertyValueFactory<>("message"));

        completedtime.setCellValueFactory(new PropertyValueFactory<>("time"));
        completedtask.setCellValueFactory(new PropertyValueFactory<>("activity"));
        completedmessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        initializeboth();
    }
    private void initializeboth() {


        notifications.append("_id", document.get("_id"))
                     .append("username", document.get("username"));

        check = m.read("notifications", notifications);
        System.out.println("\n\n chexk:::::" + check + "\n\n\n");

        if (check != null) {
            Object checkobj = check.get("notification");
            if(checkobj instanceof ArrayList){
            ArrayList<Document> allnotifications = (ArrayList<Document>)  checkobj;

            for (Document i : allnotifications) {
                if (i.get("status").toString().equals("ongoing")) {
                    Document Time = i.get("time", Document.class);
                    String hours = Time.get("hours").toString();
                    String mins = Time.get("minutes").toString();
                    String time = hours + ":" + mins;

                    String message = "";
                    if (i.get("message") != null) {
                        message = i.get("message").toString();
                    }

                    ongoingData.add(new MyData(time, i.get("activity").toString(), message));
                    System.out.println("\n\n" + ongoingData + "\n\n\n");
                } else if (i.get("status").toString().equals("completed")) {
                    Document Time = i.get("time", Document.class);
                    String hours = Time.get("hours").toString();
                    String mins = Time.get("minutes").toString();
                    String time = hours + ":" + mins;

                    String message = "";
                    if (i.get("message") != null) {
                        message = i.get("message").toString();
                    }
                    completedData.add(new MyData(time, i.get("activity").toString(), message));
                    System.out.println("\n\n" + completedData + "\n\n\n");

                } else {
                    System.err.println("can only be yes/no");
                }
            }
        }
            OngoingTable.setItems(ongoingData);
            completedTable.setItems(completedData);
        } else {
           System.err.println("check failed!");
        }
    }

    public void updatecompleted(Document notification,Document document){
        this.document=document;
       System.out.println("\n\n updated completed is called\n\n" );

       System.out.println(notification);
        Document search=new Document("_id",document.get("_id"))
                .append("username",document.get("username"));

        Document copy=new Document(search);
        System.out.println(search);

        try {
            MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
            MongoDatabase database = mongoClient.getDatabase("taskmanager");
            MongoCollection<Document> collection = database.getCollection("notifications");


            Document update = new Document("$pull", new Document("notification", notification));
            UpdateResult result = collection.updateOne(search, update);
            Document array=collection.find(search).first();
            System.out.println("Matched documents: " + result.getMatchedCount());
            System.out.println("Modified documents: " + result.getModifiedCount());
            ArrayList<Document> newarray= (ArrayList<Document>) array.get("notification");
            notification.put("status","completed");
            newarray.add(notification);
            Document update2=new Document("$set",new Document("notification",newarray));
            System.out.println("\n\n  update" + update2 + "\n\n\n");
            collection.updateOne(search,update2);
        } catch (Exception e) {
            System.err.println("Error updating completed status: " + e.getMessage());
            e.printStackTrace();
        }



       // initializeboth();

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
        viewreminderstage.close();
        stage.show();
    }

    @FXML
    private void addButton(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("setreminder.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setreminderController controller = fxmlLoader.getController();
        controller.setstage(stage);
        controller.setDocument(document);
        controller.initialize();
        stage.setScene(scene);
        viewreminderstage.close();
        stage.show();
    }

    @FXML
    private void DeleteButton(ActionEvent event) throws IOException {
        MyData choice = OngoingTable.getSelectionModel().getSelectedItem();

        if (choice != null) {
            String selectedTime    = ongoingtime.getCellData(choice);
            String selectedTask    = ongoingtask.getCellData(choice);
            String selectedMessage = ongoingmessage.getCellData(choice);

            String[] parts=selectedTime.split(":");
            Document time=new Document("hours",parts[0]).append("minutes",parts[1]);

            Document delete = new Document();
            delete.append("activity",selectedTask)
                  .append("message",selectedMessage)
                  .append("type","to-do")
                  .append("time",time);

            Stage stage =new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("DeleteConfirmation.fxml"));
            Scene scene=new Scene(fxmlLoader.load());
            DeleteConfirmationController controller= fxmlLoader.getController();
            controller.initialize("notification: "+selectedTask+" at "+selectedTime);
            controller.setstage(stage);
            stage.setScene(scene);
            stage.showAndWait();
            boolean flag=controller.checkflag();

            Document search=new Document("_id",check.get("_id"))
                                .append("username",check.get("username"));

            System.out.println("\n\n  search" + search +"\n flag"+flag+ "\n\n\n");

            if(flag){
                MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
                MongoDatabase database = mongoClient.getDatabase("taskmanager");
                MongoCollection<Document> collection = database.getCollection("notifications");
                Document update=new Document("$pull",new Document("notification",delete));
                System.out.println("\n\n  update" + update + "\n\n\n");
                collection.updateOne(search,update);

                ongoingData.remove(choice);
                n.checkupdated(true);

            }
        }
    }
    @FXML
    private void Delete1Button(ActionEvent event) throws IOException {
        MyData choice = completedTable.getSelectionModel().getSelectedItem();

        if (choice != null) {
            String selectedTime    = completedtime.getCellData(choice);
            String selectedTask    = completedtask.getCellData(choice);
            String selectedMessage = completedmessage.getCellData(choice);

            String[] parts=selectedTime.split(":");
            Document time=new Document("hours",parts[0]).append("minutes",parts[1]);

            Document delete = new Document();
            delete.append("activity",selectedTask)
                    .append("message",selectedMessage)
                    .append("type","to-do")
                    .append("time",time);

            Stage stage =new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("DeleteConfirmation.fxml"));
            Scene scene=new Scene(fxmlLoader.load());
            DeleteConfirmationController controller= fxmlLoader.getController();
            controller.initialize("notification: "+selectedTask+" at "+selectedTime);
            controller.setstage(stage);
            stage.setScene(scene);
            stage.showAndWait();
            boolean flag=controller.checkflag();

            Document search=new Document("_id",check.get("_id"))
                    .append("username",check.get("username"));

            System.out.println("\n\n  search" + search +"\n flag"+flag+ "\n\n\n");

            if(flag){
                MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
                MongoDatabase database = mongoClient.getDatabase("taskmanager");
                MongoCollection<Document> collection = database.getCollection("notifications");
                Document update=new Document("$pull",new Document("notification",delete));
                System.out.println("\n\n  update" + update + "\n\n\n");
                collection.updateOne(search,update);

                completedData.remove(choice);
                n.checkupdated(true);

            }
        }
    }
}

