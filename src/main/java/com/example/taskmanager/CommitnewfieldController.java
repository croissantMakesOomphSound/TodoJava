package com.example.taskmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CommitnewemailController {
    @FXML
   private Label newemail;
   @FXML
   private Stage stage;
   private boolean flag=false;
   public void setstage(Stage stage){
       this.stage=stage;
   }
   public void getemail(String email) throws NullPointerException{
       System.out.println(email);
       newemail.setText(email);
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
