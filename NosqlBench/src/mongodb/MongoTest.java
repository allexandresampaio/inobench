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
public class MongoTest {
    
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
            Logger.getLogger(MongoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //ABRIR THREADS AQUI PRA CADA USER
        
        int contTransacao = 0;
        for (int x = 0; x<contTransacao; x++){
            FachadaMongo.getInstancia().insert(host, port, dbName, documentos.get(i));
            //verifica se i chegou no fim da amostra
            if (i<965){
                i++;
            } else i = 0;
            contTransacao++;
        }
    }

    public void testarConsulta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
