/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongodb;

import core.Errors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import CSVreader.CSVReader;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
class MongoInsertThread extends Thread {

    private String host = "localhost";
    private String port = "27017";
    private String dbName = "teste";
    MongoFacade fachada;

    ArrayList<Document> documentos;
    CSVReader leitor = new CSVReader();

    String nome;
    int qtdTransacoes;
    
    /**
     * Construtor da classe.
     */
    public MongoInsertThread(String nome, int qtdTransacoes) {
        /* chamando o construtor de Thread passando o nome da thread como parâmetro */
        super(nome);
        this.fachada = MongoFacade.getInstancia();
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
        int i = 0;
        for (int x = 0; x < qtdTransacoes; x++) {
            Document documento = documentos.get(i);
            if (documento.containsKey("_id")) documento.remove("_id");
            
            try {
                fachada.insert(host, port, dbName, documento);
            } catch (Exception e) {
                Errors.getInstancia().marcaErro();
            }
            
            System.out.println("Thread: " + this.nome + ". Inserindo: " + x);
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
            this.TestarInsercao();
        } catch (InterruptedException ex) {
            Logger.getLogger(MongoInsertThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
