/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redis;

import mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class FachadaRedis {

    private static FachadaRedis instancia = null;

//    private static final String HOST = "localhost";
//    private static final int PORT = 27017;
//    private static final String DB_NAME = "teste";

    private FachadaRedis() {
    }

    public static FachadaRedis getInstancia() {
        if (instancia == null) {
            instancia = new FachadaRedis();
        }
        return instancia;
    }

    public MongoDatabase getDB(String HOST, String PORT, String DB_NAME) {
        int port = Integer.parseInt(PORT);
        MongoClient mongoClient = new MongoClient(HOST, port);
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);
        return db;
    }

    public MongoCollection getColecao(String HOST, String PORT, String DB_NAME, String colecao) {
        MongoCollection col = FachadaRedis.getInstancia().getDB(HOST, PORT, DB_NAME).getCollection(colecao);
        return col;
    }

    public void insert(String HOST, String PORT, String DB_NAME, Document documento) {
        this.getColecao(HOST, PORT, DB_NAME, "documentos").insertOne(documento);
    }
}
