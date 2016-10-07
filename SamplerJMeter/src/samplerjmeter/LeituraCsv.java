/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samplerjmeter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class LeituraCsv {

    ArrayList<Document> documentos = new ArrayList<>();
    
    private void criaDocumentos() throws IOException {
        String path = new File("src/arquivos/TestePlanilha.csv").getCanonicalPath();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = "";

        while ((line = br.readLine()) != null) {
            String[] linha = line.split(";");
            this.insereDocumento(linha);
        }
    }

    private void insereDocumento(String[] linha) {
        Document documento = new Document();
        documento.append("date", linha[0]).
                append("time", linha[1]).
                append("s1", linha[2]).
                append("s2", linha[3]).
                append("s3", linha[4]).
                append("s4", linha[5]).
                append("s5", linha[6]).
                append("s6", linha[7]).
                append("s7", linha[8]).
                append("s8", linha[9]).
                append("s9", linha[10]).
                append("s10", linha[11]).
                append("s11", linha[12]).
                append("s12", linha[13]).
                append("s13", linha[14]);

        documentos.add(documento);
    }

    public ArrayList<Document> getDocumentos() throws IOException {
        this.criaDocumentos();
        return documentos;
    }
}
