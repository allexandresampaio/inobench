/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchbase;

import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class CouchbaseFacade {

    private static CouchbaseFacade instancia = null;
    Bucket bucket;

    private CouchbaseFacade() {
    }

    public static CouchbaseFacade getInstancia() {
        if (instancia == null) {
            instancia = new CouchbaseFacade();
        }
        return instancia;
    }

    // couchdb-2.properties is on the classpath
    public Bucket getDB() {
        if (bucket == null) {
            // Initialize the Connection
            Cluster cluster = CouchbaseCluster.create("localhost");
            bucket =cluster.openBucket("default");
        }
        return bucket;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(Document documento) {
        String id = documento.getString("date") + documento.getString("time");
        documento.append("_id", id);
        this.getDB().save(documento);
    }

    //busca pelo _id
    public void read(String date, String time) {
        Object doc = this.getDB().find(date + time);
        System.out.println(doc);
    }
}
