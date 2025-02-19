package com.example.taskmanager;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

public class EditButtonController{
    private String selectedactivity= "";
    private String Originalactivity="-1";
    private String selectedlink= "";
    private String Originallink="-1";
   @FXML
    private ListView<TaskItem> listView;
   
    @FXML
    private Label notification;

    @FXML
    private Label notification1;

    @FXML
    private Stage stage;

    @FXML
    private ChoiceBox<TaskItem> choicebox;


    @FXML
    private TextField textField;
    @FXML
    private TextField textField1;
    private ObservableList<TaskItem> items = FXCollections.observableArrayList();

    Document document=new Document();
    @FXML
    public  void setDocument(Document document){
        this.document=document;
    }

       @FXML
    public void setstage(Stage stage){
        this.stage=stage;
    }
    public void setListView(ListView<TaskItem> listView){
        this.listView=listView;
    }

    public void intialize( ObservableList<TaskItem> items ){
        this.items=items;
        choicebox.setItems(items);
    }
    @FXML
    private void select(ActionEvent event){
        selectedactivity = choicebox.getSelectionModel().getSelectedItem().getItem();
        selectedlink=choicebox.getSelectionModel().getSelectedItem().getLink();
        textField.setText(selectedactivity);
        textField1.setText(selectedlink);

    }

    @FXML
    private void CommitButton(ActionEvent event){
        String a=textField.getText();
        String getlink=textField1.getText();
        TaskItem removetask=new TaskItem(selectedactivity,selectedlink);
        System.out.println(removetask.toString());

        items.remove(removetask);
        TaskItem addtask=new TaskItem(a,getlink);
        items.add(addtask);
        System.out.println(addtask.toString());
        RW.saveObservableListToMongo(items,"to-do",document);
        choicebox.setItems(items);
        listView.setItems(items);
        Originalactivity =a;
        Originallink=getlink;
        notification.setText("");
        notification1.setText(a+"\t"+getlink+" is added");
    }
    @FXML
    private void UndoButton(ActionEvent event){
        if(Originalactivity !=null && !Originalactivity.equals(selectedactivity) && Originalactivity !="-1") {
            TaskItem removetask=new TaskItem(Originalactivity,Originallink);
            items.remove(removetask);
            TaskItem addtask=new TaskItem(selectedactivity,selectedlink);
            items.add(addtask);

             RW.saveObservableListToMongo(items, "to-do", document);
            choicebox.setItems(items);
            listView.setItems(items);
            notification.setText(Originalactivity+"\t"+Originallink + " is removed");
            notification1.setText(selectedactivity +"\t"+selectedlink+ " is added");
        }
        else{
            notification1.setText(selectedactivity + " is already added");
        }
    }
    @FXML
    private void FinishButton(ActionEvent event){
        stage.close();
    }
}
