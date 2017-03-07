/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchbase;

import core.Errors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import CSVreader.CSVReaderToJSon;
import com.couchbase.client.java.document.json.JsonObject;

/**
 *
 * @author Allexandre
 */
class CouchbaseInsertThread extends Thread {

    CouchbaseFacade fachada;

    ArrayList<JsonObject> documentos;
    CSVReaderToJSon leitor = new CSVReaderToJSon();

    String nome;
    int qtdTransacoes;
    
    /**
     * Construtor da classe.
     */
    public CouchbaseInsertThread(String nome, int qtdTransacoes) {
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

    public void TestarInsercao() throws InterruptedException {
        int i = 0;
        for (int x = 0; x < qtdTransacoes; x++) {
            JsonObject documento = documentos.get(i);
            if (documento.containsKey("_id")) documento.removeKey("_id");
            
            try {
                fachada.insert(documento);
            } catch (Exception e) {
                Errors.getInstancia().marcaErro();
            }
            
            System.out.println("Thread: " + this.nome + ". Inserindo: " + x);
            //verifica se i chegou no fim da amostra
            //TODO deixar isso dinâmico p/ usar novos datasets
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
            this.TestarInsercao();
        } catch (InterruptedException ex) {
            Logger.getLogger(CouchbaseInsertThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
