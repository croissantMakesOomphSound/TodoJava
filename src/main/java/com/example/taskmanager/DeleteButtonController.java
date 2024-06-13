package com.example.taskmanager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteButtonController {
    @FXML
    private Label label;
    @FXML
    private ListView<String> listView;
    @FXML
    private ObservableList<String> items =RW.loadObservableListFromFile("data.txt");
    @FXML
    private ChoiceBox<String> choicebox;
    @FXML
    private Stage stage;
    @FXML
    public void intialize(){
        choicebox.setItems(items);
    }
    public void setListView(ListView<String> listView){
        this.listView=listView;
    }
    public void setstage(Stage stage){
        this.stage=stage;
    }
    @FXML
    private void DeleteButton(ActionEvent event) throws IOException {
        String selection= choicebox.getValue();
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DeleteConfirmation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DeleteConfirmationController controller = fxmlLoader.getController();
        controller.initialize(selection);
        controller.setStage(stage);
        controller.setListView(listView);
        controller.setLabel(label);
        controller.setChoiceBox(choicebox);
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    private void CancelButton(ActionEvent event) throws IOException {
        stage.close();
    }
}
