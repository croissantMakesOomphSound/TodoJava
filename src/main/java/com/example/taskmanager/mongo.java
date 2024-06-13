package com.example.taskmanager;

import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class mongo {
    public static void main(String[] args) {
        // Connect to the MongoDB server
        try (MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"))) {
            // Access the database
            MongoDatabase database = mongoClient.getDatabase("taskmanager");

            // Access the collection
            MongoCollection<Document> collection = database.getCollection("users");

            // Insert a document
            Document document = new Document("name", "Admin")
                    .append("username", "admin")
                    .append("email", "tamoghnamu@gmail.com")
                    .append("password", "1234");
            collection.insertOne(document);
            System.out.println("Document inserted successfully.");

            // Read documents
            Document query = new Document("name", "Admin");
            Document result = collection.find(query).first();
            if (result != null) {
                System.out.println("Found document: " + result.toJson());
            } else {
                System.out.println("Document not found.");
            }

            // Update a document
            collection.updateOne(Filters.eq("name", "Admin"), new Document("$set", new Document("password", "123")));
            System.out.println("Document updated successfully.");

            // Read documents after update
            result = collection.find(query).first();
            if (result != null) {
                System.out.println("Updated document: " + result.toJson());
            } else {
                System.out.println("Document not found after update.");
            }

            // Delete a document
            collection.deleteOne(query);
            System.out.println("Document deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
