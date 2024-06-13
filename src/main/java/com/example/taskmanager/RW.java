package com.example.taskmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class RW {
    public static void saveObservableListToFile(ObservableList<String> observableList, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (String item : observableList) {
                writer.println(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> loadObservableListFromFile(String fileName) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                observableList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return observableList;
    }
}
