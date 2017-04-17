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

    int port = 27017;
    private String dbName = "teste";
    MongoClient mongoClient;
    MongoDatabase db;
    private String host;

    private MongoFacade(String host) {
        this.host = host;
    }

    public static MongoFacade getInstancia(String host) {
        if (instancia == null) {
            instancia = new MongoFacade(host);
        }
        return instancia;
    }

    public MongoDatabase getDB() {
        if (mongoClient == null) {
            mongoClient = new MongoClient(host, port);
        }
        db = mongoClient.getDatabase(dbName);
        return db;
    }

    public MongoCollection getColecao(String colecao) {
        MongoCollection col = MongoFacade.getInstancia(host).getDB().getCollection(colecao);
        return col;
    }

    public void insert(Document documento) {
        this.getColecao("documentos").insertOne(documento);
    }

    public void read(String date, String time) {
        BasicDBObject includeKeys = new BasicDBObject();
        includeKeys.append("date", date);
        includeKeys.append("time", time);
        
        Object doc = this.getColecao("documentos").find(includeKeys).first();
        System.out.println(doc);
    }

}
