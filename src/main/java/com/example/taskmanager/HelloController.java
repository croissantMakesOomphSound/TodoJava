package com.example.taskmanager;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

public class HelloController implements Initializable {
    Document document=new Document();
    private Stage primarystage;
    @FXML
    private ListView<TaskItem> listView; // Annotate with @FXML to inject the ListView defined in FXML
    ObservableList<TaskItem> items ;

    public void initialize() {
        items = RW.loadtodoObservableListFromMongo("to-do", document);
        if (!items.isEmpty()) {
            listView.setItems(items);
        }

    }
    private void todoscheduler(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task= ()->{
            Platform.runLater(()->{
                notificationhandler n=new notificationhandler();
                n.notify1();});
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }
    public void setstage(Stage stage){
        primarystage=stage;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @FXML
    private void AddButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddController controller = fxmlLoader.getController();
        controller.setListview(listView);
        controller.setStage(stage);
        controller.setitemobservablelist(items);
        controller.setDocument(document);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    private void EditButton(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EditButton.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EditButtonController controller = fxmlLoader.getController();
        controller.setstage(stage);
        controller.setListView(listView);
        controller.setDocument(document);
        controller.intialize(items);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void DeleteButton(ActionEvent event) throws IOException {
        String selection=listView.getSelectionModel().getSelectedItem().toString();
        Stage stage =new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("DeleteConfirmation.fxml"));
        Scene scene=new Scene(fxmlLoader.load());
        DeleteConfirmationController controller= fxmlLoader.getController();
        controller.initialize(selection);
        controller.setstage(stage);
        stage.setScene(scene);
        stage.showAndWait();
        boolean flag=controller.checkflag();
        if(flag){
            items.remove(listView.getSelectionModel().getSelectedItem());
            RW.saveObservableListToMongo(items,"to-do",document);
            listView.setItems(items);

        }
    }

    @FXML
    private void viewprofile(MouseEvent event) throws IOException{
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewprofile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewprofileController controller = fxmlLoader.getController();
        controller.setDocument(document);
        controller.initialize();
        controller.setstage(stage);
        System.out.println("viewprofile");
        System.out.println(document.toJson());
        stage.setScene(scene);
        primarystage.close();
        stage.showAndWait();

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
        primarystage.close();
        stage.show();

    }
    @FXML
    private void recommend(MouseEvent event){
        String item=listView.getSelectionModel().getSelectedItem().getItem();
        String link=listView.getSelectionModel().getSelectedItem().getLink();
        RecommendationClass recommendationClass=new RecommendationClass();
        recommendationClass.initialize();
        recommendationClass.recommendationfortodo(item,link);

    }

    /*//////////////////////////////////////////////
    ///// calender////////////////////////////////
    ////////////////////////////////////////////*/


    private ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @FXML
    private Button goToNewPageButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();

    }


    private DailySchedulerController dailySchedulerController;
    private Stage mainStage;

    public void setDailySchedulerController(DailySchedulerController dailySchedulerController) {
        this.dailySchedulerController = dailySchedulerController;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void saveData() {
        // Implement saving data logic specific to CalendarController if needed
        System.out.println("Saving data for Calendar");
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }


    @FXML
    private void goToNewPage(ActionEvent event) {
        try {
            // Load the FXML file for the new page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DailySchedulerController.fxml"));
            Parent root = loader.load(); // Load the FXML file and get the root node

            // Access the controller of the loaded FXML
            DailySchedulerController controller = loader.getController();

            // Obtain the current date focus from your application context or method
            LocalDate dateFocus = getDateFocus(); // Replace with your method to get the date focus

            // Pass data to the DailySchedulerController
            controller.setDocument(document);

            controller.setDateFocus(dateFocus); // Pass the current date focus
            controller.loadTasksForDate(dateFocus); // Load tasks for the current date
            // Create a new scene with the new page
            Scene scene = new Scene(root);

            // Get the stage from the button and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setstage(stage);

            stage.setScene(scene);
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace(); // Handle potential errors loading the new page
        }
    }

    // Example method to retrieve the current date focus from your application
    private LocalDate getDateFocus() {
        // Replace this with your logic to obtain the current date focus
        // For example, return LocalDate.now() or retrieve from a centralized state
        return LocalDate.now();
    }






    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.SKYBLUE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);
                        rectangle.setOnMouseClicked(event -> {
                            date.getText();
                            try {
                                // Load the FXML file for the new page
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("DailySchedulerController.fxml"));
                                Parent root = loader.load(); // Load the FXML file and get the root node

                                // Access the controller of the loaded FXML
                                DailySchedulerController controller = loader.getController();

                                // Obtain the current date focus from your application context or method
                                LocalDate newdate = LocalDate.of(dateFocus.getYear(),dateFocus.getMonth(),Integer.parseInt(date.getText())); // Replace with your method to get the date focus

                                // Pass data to the DailySchedulerController
                                controller.setDocument(document);

                                controller.setDateFocus(newdate); // Pass the current date focus
                                controller.loadTasksForDate(newdate); // Load tasks for the current date
                                // Create a new scene with the new page
                                Scene scene = new Scene(root);

                                // Get the stage from the button and set the new scene
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                controller.setstage(stage);

                                stage.setScene(scene);
                                stage.show(); // Show the new scene
                            } catch (IOException e) {
                                e.printStackTrace(); // Handle potential errors loading the new page
                            }
                        });

                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                        rectangle.setStrokeWidth(3);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getClientName() + ", " + calendarActivities.get(k).getDate().toLocalTime());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
    }

    private Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity: calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if(!calendarActivityMap.containsKey(activityDate)){
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return  calendarActivityMap;
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();

        return createCalendarMap(calendarActivities);
    }
}



