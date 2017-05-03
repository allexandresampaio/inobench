/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;

/**
 *
 * @author Allexandre
 */
public class Tester {
    public static void main(String[] args) throws IOException, InterruptedException {
        Inobench tester = new Inobench();
        tester.preTeste();
        tester.testar();
        tester.posTeste();
        System.out.println("Início: " + tester.getTempoInicial());
        System.out.println("Final: " + tester.getTempoFinal());
        System.out.println("Tempo: " + tester.getDuracao() + " segundos.");
        System.out.println("Vazão: " + tester.getVazao() + " inserções por segundo.");
        System.out.println("Erros: " + tester.getErros());
        System.exit(0);
    }
}
