package com.example.taskmanager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import jdk.jfr.StackTrace;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class mongo {
        MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("taskmanager");

        public boolean create(String collection1,Document document){
            try {
                MongoCollection<Document> collection = database.getCollection(collection1);
                collection.insertOne(document);
                System.out.println("!!!!!!!!!!!!!!!!!Successfully printed!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return true;
            }

            catch(MongoWriteException e){
                System.out.println("!!!!!!!!!!!!!!!!Failed!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                e.printStackTrace();

                return false;
            }
            catch(MongoException e){
                System.out.println("!!!!!!!!!!!!!!!!!Failed2!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                return false;
            }
        }

        public Document read(String collection1, Document query){
        MongoCollection<Document> collection = database.getCollection(collection1);
        Document result = collection.find(query).first();

        if (result != null) {
            System.out.println("Found document: " + result.toJson());
            return result;
        } else {
            System.out.println("Document not found.");
            return null;
        }
    }

    public List<Document> readALL(String collection1, Document query){
        MongoCollection<Document> collection = database.getCollection(collection1);
        List<Document> result=new ArrayList<>();
       for(Document document :collection.find(query)) {
           result.add(document);
       }

        if (result.isEmpty()) {
            return result;
        } else {
            return result;
        }
    }

      public boolean update(String collection1,Document document,String field,String value){
         try{ MongoCollection<Document> collection = database.getCollection(collection1);
          Document update = new Document("$set", new Document(field,value));
          collection.updateOne(document, update);
         return true;
         }
         catch (MongoException e){
             return false;
         }

      }
    public boolean update(String collection1,Document olddocument,Document newdocument){
        try{ MongoCollection<Document> collection = database.getCollection(collection1);
            collection.deleteOne(olddocument);
            collection.insertOne(newdocument);
            return true;
        }
        catch (MongoException e){
            return false;
        }

    }
    public boolean update(String collection1,Document document,String field,ArrayList<Document> value){
        try{ MongoCollection<Document> collection = database.getCollection(collection1);
            Document update = new Document("$set", new Document(field,value));
            collection.updateOne(document, update);
            return true;
        }
        catch (MongoException e){
            return false;
        }

    }
    public boolean update(String collection1,Document document,String field,Document value){
        try{ MongoCollection<Document> collection = database.getCollection(collection1);
            Document update = new Document("$set", new Document(field,value));
            collection.updateOne(document, update);
            return true;
        }
        catch (MongoException e){
            return false;
        }

    }
    public void deleteOne(String collection1,Document searchCriteria) {
        MongoCollection<Document> collection = database.getCollection(collection1);
        DeleteResult result = collection.deleteOne(searchCriteria);
        System.out.println("Deleted document count: " + result.getDeletedCount());
    }


    /*////////////////////////////////////////////////////////////////
    //////////////////////////////Calender///////////////////////////
    //////////////////////////////////////////////////////////////*/




}
