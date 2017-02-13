/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nosqlbench;

import java.io.IOException;

/**
 *
 * @author Allexandre
 */
public class Tester {

    public static void main(String[] args) throws IOException, InterruptedException {

        NosqlBench tester = new NosqlBench();
        tester.setTempoInicial(System.currentTimeMillis());
        tester.testar();
        tester.setTempoFinal(System.currentTimeMillis());
        System.out.println("Início: " + tester.getTempoInicial());
        System.out.println("Final: " + tester.getTempoFinal());
        double tempo = tester.getTempoFinal() - tester.getTempoInicial();
        System.out.println("Tempo: " + tempo);
        long users = tester.getQtdUsers();
        long transacoes = tester.getQtdTransacoes();
        double vazao = (transacoes*users)/tempo;
        tester.setVazao(vazao);
        System.out.println("Vazão: " + tester.getVazao());
    }

}
