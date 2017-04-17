/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSVreader;

import com.couchbase.client.java.document.json.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import core.Parameters;

/**
 *
 * @author Allexandre
 */
public class CSVReaderToJSon {

    ArrayList<JsonObject> documentos = new ArrayList<>();

    private void criaDocumentos() throws IOException {
        InputStream is = CSVReaderToJSon.class.getResourceAsStream("/files/AirQualityUCI.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = "";

        while ((line = br.readLine()) != null) {
            String[] linha = line.split(";");
            this.insereDocumento(linha);
        }
        
        br.close();
        is.close();
    }

    private void insereDocumento(String[] linha) {
        JsonObject doc = JsonObject.empty()
                .put("date", linha[0])
                .put("time", linha[1])
                .put("s1", linha[2])
                .put("s2", linha[3])
                .put("s3", linha[4])
                .put("s4", linha[5])
                .put("s5", linha[6])
                .put("s6", linha[7])
                .put("s7", linha[8])
                .put("s8", linha[9])
                .put("s9", linha[10])
                .put("s10", linha[11])
                .put("s11", linha[12])
                .put("s12", linha[13])
                .put("s13", linha[14]);
        documentos.add(doc);
    }

    public ArrayList<JsonObject> getDocumentos() throws IOException {
        this.criaDocumentos();
        return documentos;
    }

    public Parameters getParametros() throws IOException {
        InputStream is = CSVReaderToJSon.class.getResourceAsStream("/files/Parameters.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = "";
        line = br.readLine();
        String[] linha = line.split(";");

        int banco = Integer.parseInt(linha[0]);
        int tipo = Integer.parseInt(linha[1]);
        int qtdUsers = Integer.parseInt(linha[2]);
        int qtdTransacoes = Integer.parseInt(linha[3]);
        String endereco = linha[4];

        Parameters parametros = new Parameters();
        parametros.setParametros(banco, tipo, qtdUsers, qtdTransacoes, endereco);

        return parametros;
    }

    public void gravarResultados(int banco, int qtdUsers, int qtdTransacoes, int tipo, long tempoInicial, long tempoFinal, double duracao, double vazao, int erros) {
        String conteudo = banco+";"+qtdUsers+";"+qtdTransacoes+";"+tipo+";"+
                tempoInicial+";"+tempoFinal+";"+duracao+";"+vazao+";"+erros+";";

        try { // o true significa q o arquivo será constante 
            File arquivo = new File("Results.csv");
            FileWriter x = new FileWriter(arquivo, true);
            
            conteudo += "\n"; // criando nova linha e recuo no arquivo 
            x.append(conteudo); // armazena o texto no objeto x, que aponta para o arquivo
            x.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
