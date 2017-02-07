/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nosqlbench;

import couchdb.CouchTest;
import java.io.IOException;
import leituraCSV.LeituraCsv;
import mongodb.MongoTest;
import redis.RedisTest;
import riakts.RiakTSTest;

/**
 *
 * @author Allexandre
 */
public class NosqlBench {
    
    //banco 1 mongo, 2 couch, 3 redis, 4 riakts
    private int banco = 0;
    //tipo 1 insercao, tipo 2 consulta
    private int tipo = 0;
    private int qtdUsers = 0;
    private int qtdTransacoes = 0;
    
    private void configurarPrametros(int banco, int tipo, int qtdUser, int qtdTransacoes){
        this.banco = banco;
        this.tipo = tipo;
        this.qtdUsers = qtdUser;
        this.qtdTransacoes = qtdTransacoes;
    }
    
    public void testar() throws IOException{
        LeituraCsv leitor = new LeituraCsv();
        Parametros p = leitor.getParametros();
        configurarPrametros(p.getBanco(), p.getTipo(), p.getQtdUsers(), p.getQtdTransacoes());
        
        switch (banco){
            case 1:
                MongoTest mongo = new MongoTest();
                mongo.setQtdUser(qtdUsers);
                mongo.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1){
                    mongo.testarInsercao();
                } else {
                    mongo.testarConsulta();
                }
                break;
            case 2:
                CouchTest couch = new CouchTest();
                couch.setQtdUser(qtdUsers);
                couch.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1){
                    couch.testarInsercao();
                } else {
                    couch.testarConsulta();
                }
                break;
            case 3:
                RedisTest redis = new RedisTest();
                redis.setQtdUser(qtdUsers);
                redis.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1){
                    redis.testarInsercao();
                } else {
                    redis.testarConsulta();
                }
                break;
            case 4:
                RiakTSTest riak = new RiakTSTest();
                riak.setQtdUser(qtdUsers);
                riak.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1){
                    riak.testarInsercao();
                } else {
                    riak.testarConsulta();
                }
                break;
            default:
                break;
        }        
    }    
}
