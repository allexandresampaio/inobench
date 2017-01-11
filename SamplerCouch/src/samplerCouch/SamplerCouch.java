/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samplerCouch;

import main.LeituraCsv;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.config.Arguments;
import org.bson.Document;

/**
 *
 * @author Allexandre
 */
public class SamplerCouch extends AbstractJavaSamplerClient {

    ArrayList<Document> documentos;
    LeituraCsv leitor = new LeituraCsv();
    int i = 0;

    //definir os argumentos para acessar o BD
    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("host", "localhost");
        defaultParameters.addArgument("port", "27017");
        defaultParameters.addArgument("dbName", "teste");
        return defaultParameters;
    }

    //This is where you read test parameters and initialize your test client. 
    //JMeter calls this method only once for each test thread.
    @Override
    public void setupTest(JavaSamplerContext context) {
        try {
            documentos = leitor.getDocumentos();
        } catch (IOException ex) {
            Logger.getLogger(SamplerCouch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Clean up the mess.
    @Override
    public void teardownTest(JavaSamplerContext context) {

    }

    //Write your test logic in this method. 
    ///JMeter will call runTest method for every execution of test threads. 
    //Here is a typical runTest implementation:
    @Override
    public SampleResult runTest(JavaSamplerContext jsc) {
        String host = jsc.getParameter("host");
        String port = jsc.getParameter("port");
        String dbName = jsc.getParameter("dbName");
        
        SampleResult result = new SampleResult();
        boolean success = true;

        result.sampleStart();
        
        //
        // Write your test code here.
        FachadaCouch.getInstancia().insert(host, port, dbName, documentos.get(i));
        i++;

        //
        result.sampleEnd();

        result.setSuccessful(success);

        return result;

    }

}
