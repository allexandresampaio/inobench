/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package influxdb;

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
class InfluxDBReadThread extends Thread {

    InfluxDBFacade fachada;
    String nome;
    int qtdTransacoes;

    ArrayList<JsonObject> documentos;
    CSVReaderToJSon leitor = new CSVReaderToJSon();

    /**
     * Construtor da classe.
     */
    public InfluxDBReadThread(String nome, int qtdTransacoes) {
        /* chamando o construtor de Thread passando o nome da thread como parâmetro */
        super(nome);
        this.fachada = InfluxDBFacade.getInstancia();
        this.qtdTransacoes = qtdTransacoes;
        this.nome = nome;
    }
    
    public void CriarArray() {
        try {
            documentos = leitor.getDocumentos();
        } catch (IOException ex) {
            Logger.getLogger(InfluxDBTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void TestarConsulta() {
        int i = 0;
        for (int x = 0; x < qtdTransacoes; x++) {
            try {
                fachada.read();
            } catch (Exception e) {
                Errors.getInstancia().marcaErro();
                System.out.println(e);
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
            Logger.getLogger(InfluxDBReadThread.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
}
