/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchbase;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

/**
 *
 * @author Allexandre
 */
public class CouchbaseFacade {

    private static CouchbaseFacade instancia = null;
    // Initialize the Connection
    Cluster cluster = null;
    Bucket bucket;
    int i = 0;

    private CouchbaseFacade(String host) {
        cluster = CouchbaseCluster.create(host);
    }

    public static CouchbaseFacade getInstancia(String host) {
        if (instancia == null) {
            instancia = new CouchbaseFacade(host);
        }
        return instancia;
    }

    // couchdb-2.properties is on the classpath
    public Bucket getDB() {
        if (bucket == null) {
            bucket = cluster.openBucket("default");
        }
        return bucket;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(JsonObject documento) {
        this.getDB().upsert(JsonDocument.create("key"+i, documento));
        i++;
    }

    //busca pelo _id
    public void read() {
        Object doc = this.getDB().get("key"+i);
        i++;
        System.out.println(doc);
    }
}
