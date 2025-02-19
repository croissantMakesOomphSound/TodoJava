package com.example.taskmanager;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        controller.setstage(stage);
        stage.setScene(scene);
        stage.show();
        todoscheduler();

    }


    private void todoscheduler(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task= ()->{
            Platform.runLater(()->{
                notificationhandler n=new notificationhandler();
                n.notify1();});
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
    }

    public static void main(String[] args) {
        launch();
    }


}
