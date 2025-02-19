module com.example.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.slf4j;
    requires org.apache.logging.log4j.slf4j.impl;
    requires org.mongodb.driver.core;
    opens com.example.taskmanager to javafx.fxml;
    exports com.example.taskmanager;
    requires java.desktop;
    requires java.management;
    requires jdk.jfr;
    requires org.apache.poi.ooxml;
}
