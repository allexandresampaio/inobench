/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchbase;

import org.bson.Document;
import com.couchbase.client.java.*;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

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
            bucket = cluster.openBucket("default");
        }
        return bucket;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(Document documento) {
        JsonObject doc = JsonObject.empty()
                .put("date", documento.get("date"))
                .put("time", documento.get("time"))
                .put("s1", documento.get("s1"))
                .put("s2", documento.get("s2"))
                .put("s3", documento.get("s3"))
                .put("s4", documento.get("s4"))
                .put("s5", documento.get("s5"))
                .put("s6", documento.get("s6"))
                .put("s7", documento.get("s7"))
                .put("s8", documento.get("s8"))
                .put("s9", documento.get("s9"))
                .put("s10", documento.get("s10"))
                .put("s11", documento.get("s11"))
                .put("s12", documento.get("s12"))
                .put("s13", documento.get("s13"));
        this.getDB().upsert(JsonDocument.create(documento.getString("date")+documento.getString("time"), doc));
    }

    //busca pelo _id
    public void read(String date, String time) {
        Object doc = this.getDB().get(date + time);
        System.out.println(doc);
    }
}
