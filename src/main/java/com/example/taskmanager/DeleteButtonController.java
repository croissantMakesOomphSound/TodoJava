package com.example.taskmanager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;

public class DeleteButtonController {
    @FXML
    private Label label;
    @FXML
    private ListView<TaskItem> listView;
    @FXML
    private ObservableList<TaskItem> items = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<TaskItem> choicebox;
    @FXML
    private Stage stage;
    Document document=new Document();
    @FXML
    public  void setDocument(Document document){
        this.document=document;
    }
    @FXML
    public void intialize(){
        choicebox.setItems(items);
    }
    public void setListView(ListView<TaskItem> listView){
        this.listView=listView;
    }
    public void setstage(Stage stage){
        this.stage=stage;
    }
    public void setobservablelist( ObservableList<TaskItem> items ){
        this.items=items;
    }
    @FXML
    private void DeleteButton(ActionEvent event) throws IOException {
        String selection= choicebox.getValue().toString();
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DeleteConfirmation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DeleteConfirmationController controller = fxmlLoader.getController();
        controller.initialize(selection);
        boolean flag=controller.checkflag();
        controller.setstage(stage);
        stage.setScene(scene);
        stage.showAndWait();
        if(flag==true){
        items.remove(selection);
        RW.saveObservableListToMongo(items,"to-do",document);
        listView.setItems(items);
        choicebox.setItems(items);
        label.setText(selection + " is deleted.");
    }
    }
    @FXML
    private void CancelButton(ActionEvent event) throws IOException {
        stage.close();
    }
}
