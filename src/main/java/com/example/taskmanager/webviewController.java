package com.example.taskmanager;

import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class webviewController  {

    @FXML
    private WebView webView;
    @FXML
    private WebEngine webEngine;
    private double zoomFactor = 0.30;
    private String url ;
    @FXML
    public void intialize(String baseurl,String query,double zoomFactor) {
        try {
            webView.setZoom(zoomFactor);
            webEngine = webView.getEngine();
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            url = baseurl + encodedQuery;
            webEngine.load(url);
        } catch (Exception e) {

        }
    }
    public void seturl(String url){
        this.url=url;
    }
    @FXML
    public void search(String queryurl,double zoomFactor){
            try {
                webView.setZoom(zoomFactor);
                webEngine = webView.getEngine();
                webEngine.load(queryurl);

            }
            catch (Exception e){

            }
        }
    @FXML
    private void redirect(MouseEvent event) throws Exception{
        Desktop.getDesktop().browse(new URI(url));

    }
}
