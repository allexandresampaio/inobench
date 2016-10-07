/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samplerjmeter;

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

    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "teste";

    private FachadaMongo() {
    }

    public static FachadaMongo getInstancia() {
        if (instancia == null) {
            instancia = new FachadaMongo();
        }
        return instancia;
    }

    public MongoDatabase getDB() {
        MongoClient mongoClient = new MongoClient(HOST, PORT);
        MongoDatabase db = mongoClient.getDatabase(DB_NAME);
        return db;
    }

    public MongoCollection getColecao(String colecao) {
        MongoCollection col = FachadaMongo.getInstancia().getDB().getCollection(colecao);
        return col;
    }

    public void insert(Document documento) {
        this.getColecao("documentos").insertOne(documento);
    }
}
