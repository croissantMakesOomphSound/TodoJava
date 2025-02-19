package com.example.taskmanager;

import java.awt.*;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecommendationClass {
    private String searchengine = new String();
    private String shoppingsite = new String();
    private String defaultIDEroot = new String();
    private String defaultpath=new String();

    private String baseurl;
    private String query;
    private double zoomFactor;
    String[] buystring = {"buy", "purchase", "from amazon", "from flipkart", "from bigbasket", "from blinkit"};
    List<String> buy = new ArrayList<>();

    webviewController window = new webviewController();
    public void initialize() {
        for (String i : buystring) {
            buy.add(i);
        }
    }

    public void setSearchengine(String searchengine) {
        this.searchengine = searchengine;
    }

    public void setShoppingsite(String shoppingsite) {
        this.shoppingsite = shoppingsite;
    }

    public void setDefaultIDEroot(String defaultIDEroot) {
        this.defaultIDEroot = defaultIDEroot;
    }

    public void setDefaultpath(String defaultpath){this.defaultpath=defaultpath;}

    private void forword(String fileName, Path directoryPath) {
        XWPFDocument document = new XWPFDocument();
        String defaultpath = "C:/Users/TMpub/OneDrive/Desktop/";
     /*   if (directoryPath.isEmpty()) {
            directoryPath = defaultpath;
        }*/

        String filePath = directoryPath + fileName;
        System.out.println("filepath"+filePath+"\n"+directoryPath);
        Path path = Paths.get(filePath);
        try {
                Desktop.getDesktop().open(path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }



    }

    /*private void forexcel(){

        String fileName = "Book5.xlsx";
        String directoryPath = "C:/Users/TMpub/OneDrive/Desktop/";

        String filePath = directoryPath + fileName;

        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            try {
                Desktop.getDesktop().open(path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    private void forppt(String fileName, Path directoryPath) {
        Path defaultpath = Paths.get("C:/Users/TMpub/OneDrive/Desktop/");
        directoryPath = defaultpath;
        String filePath = directoryPath + fileName;

        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            try {
                Desktop.getDesktop().open(path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void anyfile(String fileName, Path directoryPath) {
        String filePath = directoryPath +"."+ fileName;

        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            System.out.println("filepath"+filePath+"\n"+directoryPath);
            try {
                Desktop.getDesktop().open(path.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void forintellij(String idearoot, String projectname) {
        String ideaPath = "C:/Users/TMpub/AppData/Local/JetBrains/IntelliJ IDEA Community Edition 2024.1.2/bin/idea.bat"; // Adjust for your IntelliJ IDEA version and installation path

        String defaultroot =defaultIDEroot;
        if (ideaPath.isEmpty()) {
            idearoot = defaultroot;
        }
        String projectPath = idearoot + projectname;

        try {
            // Construct the command to open the project
            String[] command = {ideaPath, projectPath};

            // Execute the command
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            // Wait for the process to finish (optional)
            process.waitFor();

            System.out.println("IntelliJ IDEA started and project opened successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void forsearchengine() {
        switch (searchengine) {
            case "google":
                baseurl = "https://www.google.co.in/search?query=";
                zoomFactor = 0.50;
            case "duckduckgo":
                baseurl = "https://duckduckgo.com/?q=";
                zoomFactor = 0.50;
            default:
                baseurl = "https://www.google.co.in/search?query=";
                zoomFactor = 0.50;

        }
    }

    private void forshopping(String shoppingsite) {
        System.out.println("option"+shoppingsite);
        switch (shoppingsite) {
            case "amazon":
                baseurl = "https://www.amazon.in/s?k=";
                zoomFactor = 0.30;
                break;
            case "flipkart":
                baseurl = "https://www.flipkart.com/search?q=";
                zoomFactor = 0.30;
                System.out.println("flipkart printed");
                break;

            case "blinkit":
                baseurl = "https://blinkit.com/s/?q=";
                zoomFactor = 0.30;
                break;
            case "bigbasket":
                baseurl = "https://www.bigbasket.com/ps/?q=";
                zoomFactor = 0.30;
                break;
            default:
                baseurl = "https://www.amazon.in/s?k=";
                zoomFactor = 0.30;
                break;

        }
    }

    public void recommendationfortodo(String text, String filepath) {
        System.out.println(text+"\n"+filepath);

        String[] parts = text.split("\\s+");
        /*if (filepath.contains("\\")) {
            filepath = filepath.replace("\\", "/");
        }*/

        String[] path1;
        String last;
        String[] fileextension;
        String fextension;
        if (!filepath.isEmpty()) {

            //internal file handling link exits
            if (filepath.startsWith("C:") || filepath.startsWith("D:") || filepath.startsWith("E:") || filepath.startsWith("F:") || filepath.startsWith("G:") || filepath.startsWith("H:")) {
                path1 = filepath.split("\\");
                System.out.println("path" + path1);
                last = path1[path1.length - 1];
                System.out.println("last" + last);
                fileextension = last.split("\\.");
                System.out.println("fileextension" + fileextension);
                fextension = fileextension[fileextension.length - 1];
                System.out.println("fextension" + fextension);


                String path = filepath.replace(last,"");
                Path directorypath = Paths.get(path);
                System.out.println(directorypath);
                if (fextension.equals("doc") || fextension.equals("docx")) {
                    forword(last, directorypath);
                } else if (fextension.equals("ppt") || fextension.equals("pptx")) {
                    forppt(last, directorypath);
                } else if (directorypath.equals(defaultIDEroot)) {
                    forintellij(defaultIDEroot, "Calender");
                } else {
                    anyfile(last, directorypath);
                }
            }

            //browser search
            else if (!filepath.isEmpty()) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("webview.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    window = fxmlLoader.getController();
                    if (filepath.startsWith("https:") || filepath.startsWith("http://")) {
                        System.out.println("filepath inside is triggered"+filepath);
                        window.seturl(filepath);
                        window.search(filepath, 0.50);
                    } else {
                        String query = filepath;
                        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
                        String url = query + encodedQuery;
                        window.seturl(filepath);
                        window.search(url, zoomFactor);
                    }
                    stage.setScene(scene);
                    stage.showAndWait();

                } catch (IOException e) {

                }




            }
        }
        //file link is not given
        else if (filepath.isEmpty()) {
            boolean shoppingflag = false;
            for (String i : parts) {
                for (String j : buy) {
                    if (i.equals(j)) {
                        shoppingflag = true;
                    }
                }
            }
            if (shoppingflag && text.contains("from amazon")) {
                forshopping("amazon");
            }
            else if (shoppingflag && text.contains("from flipkart")) {
                forshopping("flipkart");
            }
            else if (shoppingflag && text.contains("from bigbasket")) {
                forshopping("bigbasket");
            }
            else if (shoppingflag && text.contains("from blinkit")) {
                forshopping("blinkit");
            }
            else if(shoppingflag){
                forshopping("");
            }

            if (shoppingflag) {
                String query = text;
                for(String i:buystring){
                    query=query.replace(i,"");
                }
                System.out.println("query:"+query);
                String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
                System.out.println(baseurl + "\n" + encodedQuery);
                String url = baseurl + encodedQuery;
                System.out.println(url);

                try {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("webview.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    webviewController controller = fxmlLoader.getController();
                    controller.intialize(baseurl, query, zoomFactor);
                    stage.setScene(scene);
                    stage.showAndWait();

                } catch (IOException e) {

                }


            }
        }
    }
}
