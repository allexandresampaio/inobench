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
class MongoThreadConsulta extends Thread {

    private String host = "localhost";
    private String port = "27017";
    private String dbName = "teste";
    FachadaMongo fachada;

    String nome;
    int qtdTransacoes;

    ArrayList<Document> documentos;
    LeituraCsv leitor = new LeituraCsv();

    /**
     * Construtor da classe.
     */
    public MongoThreadConsulta(String nome, int qtdTransacoes) {
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

    public void TestarConsulta() {
        int i = 0;
        for (int x = 0; x < qtdTransacoes; x++) {
            Document documento = documentos.get(i);
            
            try {
                fachada.read(host, port, dbName, documento.getString("date"), documento.getString("time"));
            } catch (Exception e) {
                Erro.getInstancia().marcaErro();
            }
            
            System.out.println("Thread: " + this.nome + ". Lendo: " + x);
            //verifica se i chegou no fim da amostra
            if (i < 964) {
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
            this.TestarConsulta();
        } catch (Exception e) {
            Logger.getLogger(MongoThreadConsulta.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
}
