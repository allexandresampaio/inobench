/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samplerjmeter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class LeituraXlsx {

    ArrayList<Document> documentos = new ArrayList<>();
    
    private void criaDocumentos() throws IOException{
        String path = new File("src/arquivos/TestePlanilha.xlsx").getCanonicalPath();

        //puxa o arquivo de planilha
        File file = new File(path);
        FileInputStream filePlanilha = null;
        XSSFWorkbook workbook = null;
        try {
            //coloca o arquivo de planilha num stream
            filePlanilha = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LeituraXlsx.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //pega todas as folhas da planilha e coloca no workbook
            workbook = new XSSFWorkbook(filePlanilha);
        } catch (IOException ex) {
            Logger.getLogger(LeituraXlsx.class.getName()).log(Level.SEVERE, null, ex);
        }

        //recuperando a primeira aba da planilha
        XSSFSheet sheet = workbook.getSheetAt(0);

        //cria um iterator que recebe a planilha.iterator
        //retornando todas as linhas da primeira aba da planilha
        Iterator<Row> rowIterator = sheet.iterator();

        //varre todas as linhas salvas no iterator
        while (rowIterator.hasNext()) {
            //pega a linha atual e salva em row
            Row row = rowIterator.next();
            Document documento = new Document();

            //cria um iterador para as celulas da linha em questao 
            Iterator<Cell> cellIterator = row.iterator();
            String[] linha = new String[15];
            int i = 0;
            

            //varrendo todas as celulas da linha
            while (cellIterator.hasNext()) {
                //pega a celula atual e salva em cell
                Cell cell = cellIterator.next();
                switch (cell.getCellType()){
                    case Cell.CELL_TYPE_NUMERIC:
                        linha[i] = cell.getNumericCellValue()+"";
                        break;
                    case Cell.CELL_TYPE_STRING:
                        linha[i] = cell.getStringCellValue();
                        break;
                }
                i++;
            }

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
    }

    public ArrayList<Document> getDocumentos() throws IOException {
        this.criaDocumentos();
        return documentos;
    }
}
