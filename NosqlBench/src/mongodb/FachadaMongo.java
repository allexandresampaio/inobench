/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class FachadaMongo {

    private static FachadaMongo instancia = null;

//    private static final String HOST = "localhost";
//    private static final int PORT = 27017;
//    private static final String DB_NAME = "teste";
    
    int port;
    MongoClient mongoClient;
    MongoDatabase db;
    
    private FachadaMongo() {
    }

    public static FachadaMongo getInstancia() {
        if (instancia == null) {
            instancia = new FachadaMongo();
        }
        return instancia;
    }

    public MongoDatabase getDB(String HOST, String PORT, String DB_NAME) {
        port = Integer.parseInt(PORT);
        if (mongoClient == null) mongoClient = new MongoClient(HOST, port);
        db = mongoClient.getDatabase(DB_NAME);

        return db;
    }

    public MongoCollection getColecao(String HOST, String PORT, String DB_NAME, String colecao) {
        MongoCollection col = FachadaMongo.getInstancia().getDB(HOST, PORT, DB_NAME).getCollection(colecao);
        return col;
    }

    public void insert(String HOST, String PORT, String DB_NAME, Document documento) {
        this.getColecao(HOST, PORT, DB_NAME, "documentos").insertOne(documento);
    }

    public void read(String HOST, String PORT, String DB_NAME) {
        //this.getColecao(HOST, PORT, DB_NAME, "documentos").find(null);
    }
}
