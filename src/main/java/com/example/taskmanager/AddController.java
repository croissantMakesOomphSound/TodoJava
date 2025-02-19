package com.example.taskmanager;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.File;

public class AddController {
    private ObservableList<TaskItem> items = FXCollections.observableArrayList();

    @FXML
    private Stage stage;

    @FXML
    private Label label1;

    @FXML
    private ListView<TaskItem> listview;


    @FXML
    private TextField inputfield;
    @FXML
    private TextField inputfield1;

    @FXML
    private static String userinput= "";
    private static String linkinput= "";
    Document document=new Document();
    @FXML
    public  void setDocument(Document document){
        this.document=document;
    }

    @FXML
    public void setListview(ListView<TaskItem> listview){
        this.listview=listview;
    }


    @FXML
    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void setitemobservablelist( ObservableList<TaskItem> items ){
        this.items=items;
    }

    @FXML
    private void SubmitButton(ActionEvent e) {
        userinput = inputfield.getText();
        linkinput=inputfield1.getText().toString();
        TaskItem newtask= new TaskItem(userinput,linkinput);
        items.add(newtask);

        RW.saveObservableListToMongo(items,"to-do",document);
        listview.setItems(items);

        label1.setText(userinput+" is added");
    }

    @FXML
    private void CancelButton(ActionEvent e){
        inputfield.clear();
        inputfield1.clear();
        TaskItem index= listview.getSelectionModel().getSelectedItem();
        items.remove(index);
        RW.saveObservableListToMongo(items,"to-do",document);

        stage.close();
    }
    @FXML
    private void file(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            inputfield1.setText(selectedFile.getAbsolutePath());
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected");
        }
    }


}
