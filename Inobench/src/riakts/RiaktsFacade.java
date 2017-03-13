/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riakts;

import com.basho.riak.client.api.RiakClient;
import java.net.UnknownHostException;
import com.couchbase.client.java.*;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

/**
 *
 * @author Allexandre
 */
public class RiaktsFacade {

    private static RiaktsFacade instancia = null;
    // Riak Client with supplied IP and Port
    RiakClient client;

    
    int i = 0;

    private RiaktsFacade() {
    }

    public static RiaktsFacade getInstancia() {
        if (instancia == null) {
            instancia = new RiaktsFacade();
        }
        return instancia;
    }

    // couchdb-2.properties is on the classpath
    public RiakClient getDB() throws UnknownHostException {
        if (client == null) {
            client =  RiakClient.newClient();
        }
        return client;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(JsonObject documento) {
        //this.getDB().upsert(JsonDocument.create("key" + i, documento));
        i++;
    }

    //busca pelo _id
    public void read() {
        //Object doc = this.getDB().get("key" + i);
        i++;
        //System.out.println(doc);
    }
}
