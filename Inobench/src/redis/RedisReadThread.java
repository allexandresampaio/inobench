/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redis;

import CSVreader.CSVReaderToDocument;
import core.Errors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
class RedisReadThread extends Thread {

    RedisFacade fachada;
    String nome;
    int qtdTransacoes;

    ArrayList<Document> documentos;
    CSVReaderToDocument leitor = new CSVReaderToDocument();

    /**
     * Construtor da classe.
     */
    public RedisReadThread(String nome, int qtdTransacoes) {
        /* chamando o construtor de Thread passando o nome da thread como parâmetro */
        super(nome);
        this.fachada = RedisFacade.getInstancia();
        this.qtdTransacoes = qtdTransacoes;
        this.nome = nome;
    }
    
    public void CriarArray() {
        try {
            documentos = leitor.getDocumentos();
        } catch (IOException ex) {
            Logger.getLogger(RedisTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RedisReadThread.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
}
