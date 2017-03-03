/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inobench;

import couchdb.CouchTest;
import java.io.IOException;
import CSVreader.LeituraCsv;
import mongodb.Erro;
import mongodb.MongoTest;
import redis.RedisTest;
import riakts.RiakTSTest;

/**
 *
 * @author Allexandre
 */
public class Inobench {

    //banco 1 mongo, 2 couch, 3 redis, 4 riakts
    private int banco = 0;
    //tipo 1 insercao, tipo 2 consulta
    private int tipo = 0;
    private int qtdUsers = 0;
    private int qtdTransacoes = 0;

    private long tempoInicial;
    private long tempoFinal;
    private double duracao;
    private double vazao;
    private int erros;

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }
    
    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }
    
    public double getVazao() {
        return vazao;
    }

    public void setVazao() {
        if (this.duracao>0) 
            this.vazao = (this.qtdUsers*this.qtdTransacoes)/this.duracao;
        else this.vazao = this.qtdUsers*this.qtdTransacoes;
    }

    public int getQtdUsers() {
        return qtdUsers;
    }

    public void setQtdUsers(int qtdUsers) {
        this.qtdUsers = qtdUsers;
    }

    public int getQtdTransacoes() {
        return qtdTransacoes;
    }

    public void setQtdTransacoes(int qtdTransacoes) {
        this.qtdTransacoes = qtdTransacoes;
    }

    public long getTempoInicial() {
        return tempoInicial;
    }

    public void setTempoInicial(long tempoInicial) {
        this.tempoInicial = tempoInicial;
    }

    public long getTempoFinal() {
        return tempoFinal;
    }

    public void setTempoFinal(long tempoFinal) {
        this.tempoFinal = tempoFinal;
    }

    private void configurarPrametros(int banco, int tipo, int qtdUser, int qtdTransacoes) {
        this.banco = banco;
        this.tipo = tipo;
        this.qtdUsers = qtdUser;
        this.qtdTransacoes = qtdTransacoes;
    }
    
    public void preTeste(){
        Erro.getInstancia().setErro(0);
        this.setTempoInicial(System.currentTimeMillis());
    }
    
    public void posTeste(){
        this.setTempoFinal(System.currentTimeMillis());
        this.setErros(Erro.getInstancia().getErros());
        this.setDuracao((this.getTempoFinal()-this.getTempoInicial())/1000.0);
        this.setVazao();  
        LeituraCsv leitor = new LeituraCsv();
        leitor.gravarResultados(this.banco, this.qtdUsers, this.qtdTransacoes, this.tipo, this.tempoInicial, this.tempoFinal, this.duracao, this.vazao, this.erros);
    }

    public void testar() throws IOException, InterruptedException {
        LeituraCsv leitor = new LeituraCsv();
        Parametros p = leitor.getParametros();
        configurarPrametros(p.getBanco(), p.getTipo(), p.getQtdUsers(), p.getQtdTransacoes());

        switch (banco) {
            case 1:
                MongoTest mongo = new MongoTest();
                mongo.setQtdUser(qtdUsers);
                mongo.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1) {
                    mongo.testarInsercao();
                } else {
                    mongo.testarConsulta();
                }
                break;
            case 2:
                CouchTest couch = new CouchTest();
                couch.setQtdUser(qtdUsers);
                couch.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1) {
                    couch.testarInsercao();
                } else {
                    couch.testarConsulta();
                }
                break;
            case 3:
                RedisTest redis = new RedisTest();
                redis.setQtdUser(qtdUsers);
                redis.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1) {
                    redis.testarInsercao();
                } else {
                    redis.testarConsulta();
                }
                break;
            case 4:
                RiakTSTest riak = new RiakTSTest();
                riak.setQtdUser(qtdUsers);
                riak.setQtdTransacoes(qtdTransacoes);
                if (tipo == 1) {
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
