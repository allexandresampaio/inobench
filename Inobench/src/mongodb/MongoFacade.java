/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class MongoFacade {

    private static MongoFacade instancia = null;

//    private static final String HOST = "localhost";
//    private static final int PORT = 27017;
//    private static final String DB_NAME = "teste";
    int port;
    MongoClient mongoClient;
    MongoDatabase db;

    private MongoFacade() {
    }

    public static MongoFacade getInstancia() {
        if (instancia == null) {
            instancia = new MongoFacade();
        }
        return instancia;
    }

    public MongoDatabase getDB(String HOST, String PORT, String DB_NAME) {
        port = Integer.parseInt(PORT);
        if (mongoClient == null) {
            mongoClient = new MongoClient(HOST, port);
        }
        db = mongoClient.getDatabase(DB_NAME);

        return db;
    }

    public MongoCollection getColecao(String HOST, String PORT, String DB_NAME, String colecao) {
        MongoCollection col = MongoFacade.getInstancia().getDB(HOST, PORT, DB_NAME).getCollection(colecao);
        return col;
    }

    public void insert(String HOST, String PORT, String DB_NAME, Document documento) {
        this.getColecao(HOST, PORT, DB_NAME, "documentos").insertOne(documento);
    }

    public void read(String HOST, String PORT, String DB_NAME, String date, String time) {
        BasicDBObject includeKeys = new BasicDBObject();
        includeKeys.append("date", date);
        includeKeys.append("time", time);
        
        Object doc = this.getColecao(HOST, PORT, DB_NAME, "documentos").find(includeKeys).first();
        System.out.println(doc);
    }

}
