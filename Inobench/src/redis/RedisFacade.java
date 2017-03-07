/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redis;

import com.couchbase.client.java.*;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import org.bson.Document;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author Allexandre
 */
public class RedisFacade {

    private static RedisFacade instancia = null;
    // Initialize the Connection
    JedisPool pool = new JedisPool("localhost");
    Jedis jedis;
    int i = 0;

    private RedisFacade() {
    }

    public static RedisFacade getInstancia() {
        if (instancia == null) {
            instancia = new RedisFacade();
        }
        return instancia;
    }

    // couchdb-2.properties is on the classpath
    public Jedis getDB() {
        if (jedis == null) {
            jedis = pool.getResource();
        }
        return jedis;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(Document d) {
        String key = i+d.getString(0)+d.getString(1);
        String value = d.toString();
        this.getDB().set(key, value);
        i++;
    }

    //busca pelo _id
    public void read(String date, String time) {
        Object doc = this.getDB().get(i+date + time);
        i++;
        System.out.println(doc);
    }
    
    public void destroyPool(){
        this.pool.destroy();
    }
}
