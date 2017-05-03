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

    //busca a instância atual da aplicação
    public static CouchbaseFacade getInstancia(String host) {
        if (instancia == null) {
            instancia = new CouchbaseFacade(host);
        }
        return instancia;
    }

    //recupera um bucket para uso
    public Bucket getDB() {
        if (bucket == null) {
            bucket = cluster.openBucket("default");
        }
        return bucket;
    }

    //insere o JSON com um id único no BD
    public void insert(JsonObject documento) {
        this.getDB().upsert(JsonDocument.create("key"+i, documento));
        i++;
    }

    //busca pelo id inserido
    public void read() {
        Object doc = this.getDB().get("key"+i);
        i++;
        System.out.println(doc);
    }
}
