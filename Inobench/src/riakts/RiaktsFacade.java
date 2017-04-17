/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riakts;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.timeseries.Query;
import com.basho.riak.client.api.commands.timeseries.Store;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;
import com.basho.riak.client.core.query.timeseries.Cell;
import com.basho.riak.client.core.query.timeseries.QueryResult;
import com.basho.riak.client.core.query.timeseries.Row;
import java.net.UnknownHostException;
import com.couchbase.client.java.document.json.JsonObject;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Allexandre
 */
public class RiaktsFacade {

    private static RiaktsFacade instancia = null;
    // Riak Client with supplied IP and Port
    RiakClient client;

    int i = 0;
    private String host;

    private RiaktsFacade(String host) {
        this.host = host;
    }

    public static RiaktsFacade getInstancia(String host) {
        if (instancia == null) {
            instancia = new RiaktsFacade(host);
        }
        return instancia;
    }

    // retorna instância do riak
    public RiakClient getDB() throws UnknownHostException {
        if (client == null) {
            client = RiakClient.newClient(host);
        }
        return client;
    }

    //cria um _id pra ser usado novamente quando for buscar os documentos
    public void insert(JsonObject documento) throws UnknownHostException, ExecutionException, InterruptedException {
        Row row = new Row(
                new Cell(i),
                new Cell(documento.getString("date")),
                new Cell(documento.getString("time")),
                new Cell(documento.getString("s1")),
                new Cell(documento.getString("s2")),
                new Cell(documento.getString("s3")),
                new Cell(documento.getString("s4")),
                new Cell(documento.getString("s5")),
                new Cell(documento.getString("s6")),
                new Cell(documento.getString("s7")),
                new Cell(documento.getString("s8")),
                new Cell(documento.getString("s9")),
                new Cell(documento.getString("s10")),
                new Cell(documento.getString("s11")),
                new Cell(documento.getString("s12")),
                new Cell(documento.getString("s13"))
        );
        Store cmd = new Store.Builder("Tabela").withRow(row).build();
        this.getDB().execute(cmd);
        i++;
    }

    //busca pelo _id
    public void read() throws ExecutionException, InterruptedException, UnknownHostException {
        String queryText = "select i, date, time from Tabela where i = " + i;
        Query query = new Query.Builder(queryText).build();
        QueryResult queryResult = this.getDB().execute(query);
        i++;
        System.out.println(queryResult);
    }

    //remoção de dados do banco, já que ele não permite a remoção de uma vez só
    public void cleanDB() throws UnknownHostException, ExecutionException, InterruptedException {
        String queryText = "delete from Tabela where i = " + i;
        Query query = new Query.Builder(queryText).build();
        this.getDB().execute(query);
        i++;
    }

    public void closeRiak() throws UnknownHostException {
        this.getDB().shutdown();
    }
}
