/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchbase;

import couchdb.*;
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
class CouchbaseReadThread extends Thread {

    CouchbaseFacade fachada;
    String nome;
    int qtdTransacoes;

    ArrayList<Document> documentos;
    CSVReader leitor = new CSVReader();

    /**
     * Construtor da classe.
     */
    public CouchbaseReadThread(String nome, int qtdTransacoes) {
        /* chamando o construtor de Thread passando o nome da thread como parâmetro */
        super(nome);
        this.fachada = CouchbaseFacade.getInstancia();
        this.qtdTransacoes = qtdTransacoes;
        this.nome = nome;
    }
    
    public void CriarArray() {
        try {
            documentos = leitor.getDocumentos();
        } catch (IOException ex) {
            Logger.getLogger(CouchbaseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TestarConsulta() {
        int i = 0;
        for (int x = 0; x < qtdTransacoes; x++) {
            Document documento = documentos.get(i);
            
            try {
                fachada.read(documento.getString("date"), documento.getString("time"));
            } catch (Exception e) {
                Errors.getInstancia().marcaErro();
            }
            
            System.out.println("Thread: " + this.nome + ". Lendo: " + x);
            //verifica se i chegou no fim da amostra
            if (i < documentos.size()-1) {
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
            Logger.getLogger(CouchbaseReadThread.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
}
