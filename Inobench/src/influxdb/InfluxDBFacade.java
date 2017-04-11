/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package influxdb;

import java.net.UnknownHostException;
import com.couchbase.client.java.document.json.JsonObject;
import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

/**
 *
 * @author Allexandre
 */
public class InfluxDBFacade {

    private static InfluxDBFacade instancia = null;
    // Riak Client with supplied IP and Port
    InfluxDB client;

    int i = 0;

    private InfluxDBFacade() {
    }

    public static InfluxDBFacade getInstancia() {
        if (instancia == null) {
            instancia = new InfluxDBFacade();
        }
        return instancia;
    }

    // retorna instância do riak
    public InfluxDB getDB() throws UnknownHostException {
        if (client == null) {
            client = InfluxDBFactory.connect("localhost:8086", "root", "root");
            //client.createDatabase("Teste");
            // Flush every 2000 Points, at least every 100ms
            client.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
        }
        return client;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(JsonObject documento) throws UnknownHostException {
        Point point1 = Point.measurement("sensor")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("i", i)
                .addField("date", documento.getString("date"))
                .addField("time", documento.getString("time"))
                .addField("s1", documento.getString("s1"))
                .addField("s2", documento.getString("s2"))
                .addField("s3", documento.getString("s3"))
                .addField("s4", documento.getString("s4"))
                .addField("s5", documento.getString("s5"))
                .addField("s6", documento.getString("s6"))
                .addField("s7", documento.getString("s7"))
                .addField("s8", documento.getString("s8"))
                .addField("s9", documento.getString("s9"))
                .addField("s10", documento.getString("s10"))
                .addField("s11", documento.getString("s11"))
                .addField("s12", documento.getString("s12"))
                .addField("s13", documento.getString("s13"))
                .build();

        this.getDB().write("Teste", "autogen", point1);
        i++;
    }

    //busca pelo _id
    public void read() {
        Query query = new Query("SELECT * FROM sensor", "Teste");
        QueryResult result = client.query(query);
        i++;
        System.out.println(result.toString());
    }

    public void closeClient() {
        this.client.close();
    }
}
