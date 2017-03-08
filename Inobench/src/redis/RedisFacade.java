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
    JedisPool pool = new JedisPool(poolConfig, "localhost");
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

    // retorna um cliente jedis da pool
    public Jedis getDB() {
        if (jedis == null) {
            jedis = pool.getResource();
        }
        return jedis;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(Document d) {
        String key = i + d.getString("date") + d.getString("time");
        String value = d.toString();
        this.getDB().set(key, value);
        i++;
    }

    //busca pelo _id
    public void read(String date, String time) {
        Object doc = this.getDB().get(i + date + time);
        i++;
        System.out.println(doc);
    }

    public void destroyPool() {
        this.pool.destroy();
    }

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
}
