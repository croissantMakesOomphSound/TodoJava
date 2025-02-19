package com.example.taskmanager;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

public class RW {
    // Assuming you have a MongoDB client instance setup somewhere
    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    static MongoDatabase database = mongoClient.getDatabase("taskmanager");

    // Method to save an ObservableList<String> to MongoDB
    public static void saveObservableListToMongo(ObservableList<TaskItem> observableList, String collectionName, Document orgdocument) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        ReplaceOptions replaceOptions = new ReplaceOptions().upsert(true);
        // Convert ObservableList<String> to List<String>
        List<Document> doclist=new ArrayList<>();
        for(TaskItem i:observableList) {
            Document newdocument=new Document("item",i.getItem())
                                 .append("link",i.getLink());
            doclist.add(newdocument);
        }

            Document document = new Document("_id", orgdocument.get("_id"))
                    .append("username", orgdocument.get("username"))
                    .append("todo", doclist);

            // Insert or update the document in the collection
            collection.replaceOne(Filters.eq("_id", orgdocument.get("_id")), document, replaceOptions);

    }

    // Method to load an ObservableList<String> from MongoDB
    public static ObservableList<TaskItem> loadtodoObservableListFromMongo(String collectionName, Document orgdocument) {
        MongoCollection<Document> collection = database.getCollection(collectionName);

        // Find the document by _id
        Document document = collection.find(Filters.eq("_id", orgdocument.get("_id"))).first();

        if (document != null) {
            // Retrieve the "todo" array field from the document
            List<Document> doclist=(List<Document>) document.get("todo");
            ObservableList<TaskItem> observableList = FXCollections.observableArrayList();
             for(Document i:doclist){
                 TaskItem newTask=new TaskItem(i.get("item").toString(),i.get("link").toString());
                 observableList.add(newTask);
             }

            return observableList;
        } else {
            System.out.println("Document not found.");
            return FXCollections.observableArrayList(); // Return empty list if document is not found
        }
    }


}
