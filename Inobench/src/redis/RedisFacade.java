/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redis;

import java.time.Duration;
import org.bson.Document;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author Allexandre
 */
public class RedisFacade {

    private static RedisFacade instancia = null;
    // Initialize the Connection
    final JedisPoolConfig poolConfig = buildPoolConfig();
    JedisPool pool = null;
    Jedis jedis;
    int i = 0;

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1100);
        poolConfig.setMaxIdle(16);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    private RedisFacade(String host) {
        pool = new JedisPool(poolConfig, host);
    }

    public static RedisFacade getInstancia(String host){
        if (instancia == null) {
            instancia = new RedisFacade(host);
        }
        return instancia;
    }

    // retorna um cliente jedis da pool
    public Jedis getDB() {
        jedis = pool.getResource();
        return jedis;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(Document d) {
        String key = "key" + i;
        String value = d.toString();
        Jedis jedis = this.getDB();
        jedis.set(key, value);
        jedis.close();
        i++;
    }

    //busca pelo _id
    public void read() {
        Jedis jedis = this.getDB();
        Object doc = jedis.get("key" + i);
        jedis.close();
        i++;
        System.out.println(doc);
    }

    public void destroyPool() {
        this.pool.destroy();
    }
}
