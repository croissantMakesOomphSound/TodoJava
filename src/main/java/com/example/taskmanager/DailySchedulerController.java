package com.example.taskmanager;

import com.mongodb.MongoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DailySchedulerController implements Initializable {

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> timeColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, String> categoryColumn;

    @FXML
    private TableColumn<Task, String> notesColumn;

    @FXML
    private TableColumn<Task, String> completedColumn; // Changed to String type

    @FXML
    private TableColumn<Task, Void> editColumn;
    @FXML
    private TableColumn<Task, String> linkColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeInput;

    @FXML
    private TextField taskInput;

    @FXML
    private TextField notesInput;
    @FXML
    private TextField linkInput;

    @FXML
    private Text currentDateText;

    @FXML
    private Text progressText;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private static volatile DailySchedulerController instance;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    private LocalDate dateFocus;

    private Stage DailySchedulerStage;
    private Document document;
    mongo m=new mongo();

    @FXML
    public void setstage(Stage stage){
        DailySchedulerStage=stage;
    }
    public void setDocument(Document document){
        this.document=document;
        System.out.println("\n\n\n\n\n\ndocument is set\n\n\n"+document+"\n\n\n\n");
    }
    @FXML
    private void HomeButton(MouseEvent event) throws IOException {
        System.out.println("homebutton");
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Taskmanager.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController controller = fxmlLoader.getController();
        controller.setDocument(document);
        controller.initialize();
        controller.setstage(stage);
        stage.setScene(scene);
        DailySchedulerStage.close();
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
        DailySchedulerStage.close();
        stage.show();

    }

    @FXML
    private void backoneday(MouseEvent event){
        dateFocus=dateFocus.minusDays(1);
        datePicker.setValue(dateFocus);
        showTasksForSelectedDate();
    }

    @FXML
    private void forwardoneday(MouseEvent event){
        dateFocus=dateFocus.plusDays(1);
        datePicker.setValue(dateFocus);
        showTasksForSelectedDate();
    }

    public static DailySchedulerController getInstance() {
        if (instance == null) {
            synchronized (DailySchedulerController.class) {
                if (instance == null) {
                    instance = new DailySchedulerController();
                }
            }
        }
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize table columns
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        // Initialize completed column with ChoiceBox
        completedColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));
        completedColumn.setCellFactory(param -> new TableCell<>() {
            private final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Yes", "No"));

            {
                choiceBox.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    task.setCompleted(choiceBox.getValue());
                    updateItem(task.getCompleted(), isEmpty());
                    saveData(); // Save data when completed state changes
                    updateProgress();
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    choiceBox.setValue(item);
                    setGraphic(choiceBox);
                }
            }
        });
        linkColumn.setCellValueFactory(new  PropertyValueFactory<>("link"));

        // Initialize edit column with button
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    editTask(task);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Initialize date picker with today's date
        datePicker.setValue(LocalDate.now());

        // Initialize category choice box items
        categoryChoiceBox.setItems(FXCollections.observableArrayList("Urgent", "Personal", "Business"));

        // Listener to update tasks when the date picker value changes
        datePicker.setOnAction(event -> showTasksForSelectedDate());


        // Calculate and display progress for the selected date
        updateProgress();


    }
    @FXML
    private void file(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(DailySchedulerStage);
        if (selectedFile != null) {
            linkInput.setText(selectedFile.getAbsolutePath());
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected");
        }
    }
    @FXML
    private void recommend(MouseEvent event){
        Task task = taskTable.getSelectionModel().getSelectedItem();
       String selection=task.getTask();
        String link=task.getLink();
        RecommendationClass recommendationClass=new RecommendationClass();
        recommendationClass.initialize();
        recommendationClass.recommendationfortodo(selection,link);

    }

    @FXML
    private void showTasksForSelectedDate() throws NullPointerException {
        LocalDate selectedDate = datePicker.getValue();
        currentDateText.setText("Current Date: " + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        tasks.clear();
        Document search=new Document("User_id",document.get("_id"))
                        .append("username",document.get("username"))
                        .append("date",selectedDate);
        Document check=m.read("calender",search);
        if(check!=null){
            try{
                List<Document> existingtasks = (List<Document>) check.get("tasks");
                ArrayList<Document> olddocument_tasks_ArrayList =new ArrayList<>(existingtasks);
                for(Document i:olddocument_tasks_ArrayList){
                    tasks.add(new Task(selectedDate.toString()
                            ,i.get("time").toString()
                            ,i.get("task").toString()
                            ,i.get("category").toString()
                            ,i.get("notes").toString()
                            ,i.get("completed").toString()
                            ,i.get("link").toString()
                    ));
                }
            }
            catch (MongoException e){
                e.printStackTrace();
            }
        }
        else{

        }

        updateProgress();
    }

    @FXML
    void addTask(ActionEvent event) {
        String time = timeInput.getText();
        String task = taskInput.getText();
        String category = categoryChoiceBox.getValue();
        String notes = notesInput.getText();
        LocalDate selectedDate = datePicker.getValue();
        String newlink=linkInput.getText();

        if (!time.isEmpty() && !task.isEmpty() && selectedDate != null && category != null) {
            Task newTask = new Task(selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), time, task, category, notes, "No",newlink);
            tasks.add(newTask);
            saveData();

            // Update progress for the selected date
            updateProgress();
        }
    }

    @FXML
    void removeTask(ActionEvent event) {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
            saveupdateData();
            // Update progress for the selected date
            updateProgress();
        }
    }

    public void saveData() {
        ArrayList<Document> newdocument_tasks_ArrayList = new ArrayList<Document>();

        Document calender = new Document("User_id", document.get("_id"))
                .append("username", document.get("username"))
                .append("date", datePicker.getValue());

        System.out.println("Created calendar document: " + calender);

        Document result = m.read("calender", calender);
        System.out.println("Read result: " + result);

        for (Task task : tasks) {
            Document document_tasks = new Document("time", task.getTime())
                    .append("task", task.getTask())
                    .append("notes", task.getNotes())
                    .append("category", task.getCategory())
                    .append("completed", task.getCompleted())
                    .append("link",task.getLink());

            newdocument_tasks_ArrayList.add(document_tasks);

        }
        System.out.println("\n\n\n"+newdocument_tasks_ArrayList+"\n\n\n\n\n");

        System.out.println("Final calendar document with tasks: " + calender);

        if (result == null) {
            calender.append("tasks", newdocument_tasks_ArrayList);
            m.create("calender", calender);
            System.out.println("Document created: " + calender);
        } else {
            m.update("calender", calender, "tasks", newdocument_tasks_ArrayList);
            System.out.println("Document updated: " + calender);
        }
    }


    public void saveupdateData() {
        ArrayList<Document> newdocument_tasks_ArrayList = new ArrayList<Document>();
        Document calender = new Document("User_id", document.get("_id"))
                .append("username", document.get("username"))
                .append("date", datePicker.getValue());

        Document result = m.read("calender", calender);
        m.deleteOne("calender", calender);

        for (Task task : tasks) {
            Document document_tasks=new Document("time",task.getTime())
                    .append("task",task.getTask())
                    .append("notes",task.getNotes())
                    .append("category",task.getCategory())
                    .append("completed",task.getCompleted())
                    .append("link",task.getLink());

            newdocument_tasks_ArrayList.add(document_tasks);

        }
        m.update("calender", calender,"tasks",newdocument_tasks_ArrayList);
    }

    public void setDateFocus(LocalDate dateFocus) {
        this.dateFocus=dateFocus;
        datePicker.setValue(dateFocus);
        showTasksForSelectedDate();
    }

    public void loadTasksForDate(LocalDate date) {
               tasks.clear();


        Document search=new Document("User_id",document.get("_id"))
                .append("username",document.get("username"))
                .append("date",date);
        Document check=m.read("calender",search);

        if(check!=null && date!=null){
            try{
                List<Document> existingtasks = (List<Document>) check.get("tasks");
                ArrayList<Document> olddocument_tasks_ArrayList =new ArrayList<>(existingtasks);
                System.out.println(check);
                System.out.println(olddocument_tasks_ArrayList);

                for(Document i:olddocument_tasks_ArrayList){
                    tasks.add(new Task(date.toString()
                            ,i.get("time").toString()
                            ,i.get("task").toString()
                            ,i.get("category").toString()
                            ,i.get("notes").toString()
                            ,i.get("completed").toString()
                            ,i.get("link").toString()
                    ));
                }
            }
            catch (MongoException e){
                e.printStackTrace();
            }
        }
        else{

        }

        taskTable.setItems(tasks);
        updateProgress();
    }

    @FXML
    private void clearInputs() {
        timeInput.clear();
        taskInput.clear();
        categoryChoiceBox.setValue(null);
        notesInput.clear();
        linkInput.clear();
    }

    private void editTask(Task task) {
        timeInput.setText(task.getTime());
        taskInput.setText(task.getTask());
        categoryChoiceBox.setValue(task.getCategory());
        notesInput.setText(task.getNotes());
        linkInput.setText(task.getLink());

        tasks.remove(task); // Remove the existing task to allow for editing
        saveupdateData();;

        updateProgress(); // Update progress as task count may change
    }

    private void updateProgress() {
        LocalDate selectedDate = datePicker.getValue();
        int totalTasks = tasks.size();
        if (totalTasks == 0) {
            progressText.setText("No tasks for selected date.");
            return;
        }

        long completedTasks = tasks.stream().filter(task -> task.getCompleted().equals("Yes")).count();
        double progressPercentage = (double) completedTasks / totalTasks * 100;
        progressText.setText(String.format("Progress: %.2f%%", progressPercentage));
    }

    public static class Task {
        private final StringProperty date;
        private final StringProperty time;
        private final StringProperty task;
        private final StringProperty category;
        private final StringProperty notes;
        private final StringProperty completed;
        private final StringProperty link;

        public Task(String date, String time, String task, String category, String notes, String completed,String link) {
            this.date = new SimpleStringProperty(date);
            this.time = new SimpleStringProperty(time);
            this.task = new SimpleStringProperty(task);
            this.category = new SimpleStringProperty(category);
            this.notes = new SimpleStringProperty(notes);
            this.completed = new SimpleStringProperty(completed);
            this.link=new SimpleStringProperty(link);
        }

        public String getDate() {
            return date.get();
        }

        public StringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getTime() {
            return time.get();
        }

        public StringProperty timeProperty() {
            return time;
        }

        public void setTime(String time) {
            this.time.set(time);
        }

        public String getTask() {
            return task.get();
        }

        public StringProperty taskProperty() {
            return task;
        }

        public void setTask(String task) {
            this.task.set(task);
        }

        public String getCategory() {
            return category.get();
        }

        public StringProperty categoryProperty() {
            return category;
        }

        public void setCategory(String category) {
            this.category.set(category);
        }

        public String getNotes() {
            return notes.get();
        }

        public StringProperty notesProperty() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes.set(notes);
        }

        public String getCompleted() {
            return completed.get();
        }

        public StringProperty completedProperty() {
            return completed;
        }

        public void setCompleted(String completed) {
            this.completed.set(completed);
        }
        public String getLink(){
            return link.get();
        }
    }
}

