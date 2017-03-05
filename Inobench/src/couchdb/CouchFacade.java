/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchdb;

import mongodb.*;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.lightcouch.CouchDbClient;

/**
 *
 * @author Allexandre
 */
public class CouchFacade {

    private static CouchFacade instancia = null;
    CouchDbClient couchClient;

    private CouchFacade() {
    }

    public static CouchFacade getInstancia() {
        if (instancia == null) {
            instancia = new CouchFacade();
        }
        return instancia;
    }
    
    // couchdb-2.properties is on the classpath
    public CouchDbClient getDB(){
        if (couchClient == null){
            couchClient = new CouchDbClient("/files/couchdb.properties");
        }
        return couchClient;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(Document documento) {
        String id = documento.getString("date")+documento.getString("time");
        documento.append("_id", id);
        this.getDB().save(documento);
    }

    //busca pelo _id
    public void read(String date, String time) {
        Object doc = this.getDB().find(date+time);
        System.out.println(doc);
    }
}
