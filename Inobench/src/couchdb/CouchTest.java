/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package couchdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import CSVreader.LeituraCsv;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class CouchTest {
    
    private String host = "localhost";
    private String port = "27017";
    private String dbName = "teste";
    private int i = 0;
    
    private int qtdUser;
    private int qtdTransacoes;

    public int getQtdUser() {
        return qtdUser;
    }

    public void setQtdUser(int qtdUser) {
        this.qtdUser = qtdUser;
    }

    public int getQtdTransacoes() {
        return qtdTransacoes;
    }

    public void setQtdTransacoes(int qtdTransacoes) {
        this.qtdTransacoes = qtdTransacoes;
    }

    ArrayList<Document> documentos;
    LeituraCsv leitor = new LeituraCsv();  
    
    public void testarInsercao() {
        try {
            documentos = leitor.getDocumentos();
        } catch (IOException ex) {
            Logger.getLogger(CouchTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO FAZER FACHADA NOVA
        //FachadaMongo.getInstancia().insert(host, port, dbName, documentos.get(i));
    }

    public void testarConsulta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
