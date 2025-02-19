package com.example.calender;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private DatePicker datePicker;

    @FXML
    private TextField timeInput;

    @FXML
    private TextField taskInput;

    @FXML
    private TextField notesInput;

    @FXML
    private Text currentDateText;

    @FXML
    private Text progressText;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    private static volatile DailySchedulerController instance;

    private ObservableList<Task> tasks = FXCollections.observableArrayList();

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

        // Show tasks for the selected date in the date picker
        showTasksForSelectedDate();

        // Calculate and display progress for the selected date
        updateProgress();
    }

    @FXML
    private void showTasksForSelectedDate() {
        LocalDate selectedDate = datePicker.getValue();
        currentDateText.setText("Current Date: " + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        tasks.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    try {
                        LocalDate taskDate = LocalDate.parse(parts[0]);
                        if (taskDate.equals(selectedDate)) {
                            tasks.add(new Task(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5])); // Use parts[5] directly for completed
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Error parsing date: " + parts[0] + ". Skipping this line.");
                        continue;
                    }
                } else {
                    System.err.println("Invalid line format in tasks.txt: " + line);
                }
            }
            taskTable.setItems(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update progress for the selected date
        updateProgress();
    }

    @FXML
    void addTask(ActionEvent event) {
        String time = timeInput.getText();
        String task = taskInput.getText();
        String category = categoryChoiceBox.getValue();
        String notes = notesInput.getText();
        LocalDate selectedDate = datePicker.getValue();

        if (!time.isEmpty() && !task.isEmpty() && selectedDate != null && category != null) {
            Task newTask = new Task(selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), time, task, category, notes, "No");
            tasks.add(newTask);
            saveData();
            clearInputs();

            // Update progress for the selected date
            updateProgress();
        }
    }

    @FXML
    void removeTask(ActionEvent event) {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
            saveData();

            // Update progress for the selected date
            updateProgress();
        }
    }

    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.getDate() + "," + task.getTime() + "," + task.getTask() + "," + task.getCategory() + "," + task.getNotes() + "," + task.getCompleted());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Saved data to file.");
    }

    public void setDateFocus(LocalDate dateFocus) {
        datePicker.setValue(dateFocus);
        showTasksForSelectedDate();
    }

    public void loadTasksForDate(LocalDate date) {
        tasks.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    try {
                        LocalDate taskDate = LocalDate.parse(parts[0]);
                        if (taskDate.equals(date)) {
                            tasks.add(new Task(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5])); // Use parts[5] directly for completed
                        }
                    } catch (DateTimeParseException e) {
                        System.err.println("Error parsing date: " + parts[0] + ". Skipping this line.");
                        continue;
                    }
                } else {
                    System.err.println("Invalid line format in tasks.txt: " + line);
                }
            }
            System.out.println("Loaded tasks for date " + date + ": " + tasks.size());
        } catch (IOException e) {
            e.printStackTrace();
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
    }

    private void editTask(Task task) {
        timeInput.setText(task.getTime());
        taskInput.setText(task.getTask());
        categoryChoiceBox.setValue(task.getCategory());
        notesInput.setText(task.getNotes());

        tasks.remove(task); // Remove the existing task to allow for editing

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
        private final StringProperty completed; // Changed to StringProperty

        public Task(String date, String time, String task, String category, String notes, String completed) {
            this.date = new SimpleStringProperty(date);
            this.time = new SimpleStringProperty(time);
            this.task = new SimpleStringProperty(task);
            this.category = new SimpleStringProperty(category);
            this.notes = new SimpleStringProperty(notes);
            this.completed = new SimpleStringProperty(completed); // Initialize completed as StringProperty
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
    }
}

