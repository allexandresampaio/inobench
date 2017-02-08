/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongodb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import leituraCSV.LeituraCsv;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
class MongoThreadInsercao extends Thread {

    private String host = "localhost";
    private String port = "27017";
    private String dbName = "teste";
    private int i = 0;
    FachadaMongo fachada;

    ArrayList<Document> documentos;
    LeituraCsv leitor = new LeituraCsv();

    String nome;
    int qtdTransacoes;	

    /**
     * Construtor da classe.
     */
    public MongoThreadInsercao(String nome, int qtdTransacoes) {
        /* chamando o construtor de Thread passando o nome da thread como parâmetro */
        super(nome);
        this.fachada = FachadaMongo.getInstancia();
        this.qtdTransacoes = qtdTransacoes;
        this.nome = nome;
    }

    public void CriarArray() {
        try {
            documentos = leitor.getDocumentos();
        } catch (IOException ex) {
            Logger.getLogger(MongoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TestarInsercao() throws InterruptedException {
        for (int x = 0; x < qtdTransacoes; x++) {
            fachada.insert(host, port, dbName, documentos.get(i));
            
            System.out.println("Thread: " + this.nome + ". Inserindo: " + x);
            //Thread.sleep(1000);//pausa por 1 segundo
            //verifica se i chegou no fim da amostra
            if (i < 950) {
                i++;
            } else {
                i = 0;
            }
        }
    }

    /**
     * Método run da thread
     */
    public void run() {
        this.CriarArray();
        try {
            this.TestarInsercao();
        } catch (InterruptedException ex) {
            Logger.getLogger(MongoThreadInsercao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
