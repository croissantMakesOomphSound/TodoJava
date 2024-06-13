package com.example.taskmanager;
import javafx.scene.control.ChoiceBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditButtonController{
    private String selected=new String();
    private String Original=new String();
   @FXML
    private ListView<String> listView;
    @FXML
    private Label notification;

    @FXML
    private Label notification1;

    @FXML
    private Stage stage;

    @FXML
    private ChoiceBox<String> choicebox;

    @FXML
    private TextField textField;
    ObservableList<String> items = RW.loadObservableListFromFile("data.txt");

    @FXML
    public void setstage(Stage stage){
        this.stage=stage;
    }
    public void setListView(ListView<String> listView){
        this.listView=listView;
    }
    public void intialize(){
        choicebox.setItems(items);
        choicebox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textField.setText(newValue);
            }
        });

    }
    @FXML
    private void select(ActionEvent event){
        selected = choicebox.getSelectionModel().getSelectedItem();

    }
    @FXML
    private void CommitButton(ActionEvent event){
        String a=textField.getText();
        items.remove(selected);
        items.add(a);
        RW.saveObservableListToFile(items, "data.txt");
        choicebox.setItems(items);
        listView.setItems(items);
        Original=a;
        notification.setText("");
        notification1.setText(a+" is added");
    }
    @FXML
    private void UndoButton(ActionEvent event){
        System.out.println("s"+selected);
        System.out.println("o"+Original);
        items.remove(Original);
        items.add(selected);
        RW.saveObservableListToFile(items, "data.txt");
        choicebox.setItems(items);
        listView.setItems(items);
        notification.setText(Original+" is removed");
        notification1.setText(selected+" is added");
    }
    @FXML
    private void FinishButton(ActionEvent event){
        stage.close();
    }
}
