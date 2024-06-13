package com.example.taskmanager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class AddController {
    ObservableList<String> items=RW.loadObservableListFromFile("data.txt");
    @FXML
    private Stage stage;

    @FXML
    private Label label1;

    @FXML
    private ListView<String> listview;

    @FXML
    private TextField inputfield;

    @FXML
    static String userinput= new String();

    @FXML
    public void setListview(ListView<String> listview){
        this.listview=listview;
    }

    @FXML
    public void setStage(Stage stage){
        this.stage=stage;
    }
    @FXML
    private void SubmitButton(ActionEvent e) {
        userinput = inputfield.getText();
        items.add(userinput);
        listview.setItems(items);
        RW.saveObservableListToFile(items,"data.txt");
        label1.setText(userinput+" is added");
    }

    @FXML
    private void CancelButton(ActionEvent e){
        inputfield.clear();
        userinput = " ";
        items.remove(userinput);
        RW.saveObservableListToFile(items,"data.txt");
        stage.close();
    }
    public  static String getUserInput(){
        return  userinput;
    }


}
