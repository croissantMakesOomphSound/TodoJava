package com.example.taskmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CommitnewfieldController {
   @FXML
   private Label newfield;
   @FXML
   private Label newvalue;
   @FXML
   private Stage stage;
   private boolean flag=false;
   public void setstage(Stage stage){
       this.stage=stage;
   }
   public void getfield(String field,String value) throws NullPointerException{
       newfield.setText(field+" to :");
       newvalue.setText(value);
   }
   public boolean check(){
       return flag;
   }
   @FXML
   private void YesButton(MouseEvent event){
        flag=true;
        stage.close();
   }
   @FXML
   private void CancelButton(MouseEvent event){
       flag=false;
       stage.close();
   }
}
