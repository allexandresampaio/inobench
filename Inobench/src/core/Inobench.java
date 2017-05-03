/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import CSVreader.CSVReaderToDocument;
import couchbase.CouchbaseTest;
import mongodb.MongoTest;
import redis.RedisTest;

/**
 * Classe principal da aplicação
 * @author Allexandre
 */
public class Inobench {

    //banco 1 mongo, 2 couch, 3 redis
    private int banco = 0;
    //tipo 1 insercao, tipo 2 consulta
    private int tipo = 0;
    //quantidade de usuários simultâneos
    private int qtdUsers = 0;
    //quantidade de transações para cada usuário
    private int qtdTransacoes = 0;
    //endereço do host do banco de dados
    private String endereco;
    private long tempoInicial;
    private long tempoFinal;
    private double duracao;
    private double vazao;
    private int erros;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
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
    //calcula vazão média de todas as operações
    public void setVazao() {
        if (this.duracao > 0) {
            this.vazao = (this.qtdUsers * this.qtdTransacoes) / this.duracao;
        } else {
            this.vazao = this.qtdUsers * this.qtdTransacoes;
        }
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
    //seta no objeto os parâmetros recebidos
    private void configurarPrametros(int banco, int tipo, int qtdUser, int qtdTransacoes, String endereco) {
        this.banco = banco;
        this.tipo = tipo;
        this.qtdUsers = qtdUser;
        this.qtdTransacoes = qtdTransacoes;
        this.endereco = endereco;
    }
    //pré-processamento: zera a ocorrência de erros e grava o tempo inicial da execução
    public void preTeste() {
        Errors.getInstancia().setErro(0);
        this.setTempoInicial(System.currentTimeMillis());
    }

    //pós-processamento: grava o tempo final da execução, 
    //seta a quantidade de erros, 
    //calcula a duração da execução
    //calcula vazão média
    //grava todos os resultados no arquivo de resultados
    public void posTeste() {
        this.setTempoFinal(System.currentTimeMillis());
        this.setErros(Errors.getInstancia().getErros());
        this.setDuracao((this.getTempoFinal() - this.getTempoInicial()) / 1000.0);
        this.setVazao();
        CSVReaderToDocument leitor = new CSVReaderToDocument();
        leitor.gravarResultados(this.banco, this.qtdUsers, this.qtdTransacoes, this.tipo, this.tempoInicial, this.tempoFinal, this.duracao, this.vazao, this.erros);
    }

    //controla todos os testes
    public void testar() throws IOException, InterruptedException {
        CSVReaderToDocument leitor = new CSVReaderToDocument();
        Parameters p = leitor.getParametros();
        configurarPrametros(p.getBanco(), p.getTipo(), p.getQtdUsers(), p.getQtdTransacoes(), p.getEndereco());

        switch (banco) {
            case 1:
                MongoTest mongo = new MongoTest();
                mongo.setQtdUser(qtdUsers);
                mongo.setQtdTransacoes(qtdTransacoes);
                mongo.setEndereco(endereco);
                if (tipo == 1) {
                    mongo.testarInsercao();
                } else {
                    mongo.testarConsulta();
                }
                break;
            case 2:
                CouchbaseTest couch = new CouchbaseTest();
                couch.setQtdUser(qtdUsers);
                couch.setQtdTransacoes(qtdTransacoes);
                couch.setEndereco(endereco);
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
                redis.setEndereco(endereco);
                if (tipo == 1) {
                    redis.testarInsercao();
                } else {
                    redis.testarConsulta();
                }
                break;
            default:
                break;
        }
    }
}
