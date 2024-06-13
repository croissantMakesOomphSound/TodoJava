package com.example.taskmanager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class DeleteConfirmationController {
    ObservableList<String> items =RW.loadObservableListFromFile("data.txt");
    String selection;
    @FXML
    private Label label;
    @FXML
    private Stage stage;
    @FXML
    private ChoiceBox<String> choicebox;
    @FXML
    private ListView<String> listView;

    public void setChoiceBox(ChoiceBox<String> choicebox) {
        this.choicebox = choicebox;
    }

    public void setLabel(Label label){
        this.label=label;
    }
    public  void setListView(ListView<String> listView){
        this.listView=listView;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(String selection) {
        label.setText(selection);
        this.selection=selection;
    }
    @FXML
    private void DeleteButton(ActionEvent event) {
        items.remove(selection);
        RW.saveObservableListToFile(items,"data.txt");
        listView.setItems(items);
        choicebox.setItems(items);
        label.setText(selection + " is deleted.");
        stage.close();

    }
    @FXML
    private void CancelButton(ActionEvent event){
            stage.close();
    }


}
