package com.example.taskmanager;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;


public class HelloController {
    private Stage primarystage;
    @FXML
    private ListView<String> listView; // Annotate with @FXML to inject the ListView defined in FXML
    ObservableList<String> items = RW.loadObservableListFromFile("data.txt");

    public void initialize() {
        listView.setItems(items);
        listView.setEditable(true);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                listView.edit(listView.getSelectionModel().getSelectedIndex());
            }
        });
    }

    public void setstage(Stage stage){
        primarystage=stage;
    }

    @FXML
    private void AddButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AddController controller = fxmlLoader.getController();
        controller.setListview(listView);
        controller.setStage(stage);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    private void EditButton(ActionEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EditButton.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EditButtonController controller = fxmlLoader.getController();
        controller.intialize();
        controller.setstage(stage);
        controller.setListView(listView);
        stage.setScene(scene);
        stage.showAndWait();
        }

        @FXML
    private void DeleteButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DeleteButton.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DeleteButtonController controller = fxmlLoader.getController();
        controller.intialize();
        controller.setstage(stage);
        controller.setListView(listView);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    private void viewprofile(MouseEvent event) throws IOException{
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("viewprofile.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        viewprofileController controller = fxmlLoader.getController();
        controller.setstage(stage);
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
        stage.setScene(scene);
        primarystage.close();
        stage.show();

    }

}



