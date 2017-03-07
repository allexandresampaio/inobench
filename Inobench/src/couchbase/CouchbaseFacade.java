/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchbase;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;

/**
 *
 * @author Allexandre
 */
public class CouchbaseFacade {

    private static CouchbaseFacade instancia = null;
    // Initialize the Connection
    Cluster cluster = CouchbaseCluster.create("localhost");
    Bucket bucket;
    int i = 0;

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
            bucket = cluster.openBucket("default");
        }
        return bucket;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(JsonObject documento) {
        this.getDB().upsert(JsonDocument.create(i+documento.getString("date")
                + documento.getString("time"), documento));
        i++;
    }

    //busca pelo _id
    public void read(String date, String time) {
        Object doc = this.getDB().get(i+date + time);
        Object doc2 = this.getDB().query(select("*").from("default").where(x(date).eq(s(date))));
        i++;
        //System.out.println(doc);
        System.out.println(doc2);
    }
}
