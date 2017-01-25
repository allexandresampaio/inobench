/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leituraCSV;

import java.io.IOException;
import java.util.ArrayList;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class TestaLeituraCsv {
    public static void main(String[] args) throws IOException {
        ArrayList<Document> array;
        LeituraCsv leitor = new LeituraCsv();
        array = leitor.getDocumentos();
        
        System.out.println(array);
    }
}
