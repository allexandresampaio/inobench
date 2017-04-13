/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riakts;

import core.Errors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Allexandre
 */
class RiaktsDeleteThread extends Thread {

    RiaktsFacade fachada;
    String nome;
    int qtdTransacoes;

        /**
     * Construtor da classe.
     */
    public RiaktsDeleteThread(String nome, int qtdTransacoes) {
        /* chamando o construtor de Thread passando o nome da thread como parâmetro */
        super(nome);
        this.fachada = RiaktsFacade.getInstancia();
        this.qtdTransacoes = qtdTransacoes;
        this.nome = nome;
    }
    

    public void Deletar() {
        int i = 0;
        for (int x = 0; x < qtdTransacoes; x++) {
            try {
                fachada.cleanDB();
            } catch (Exception e) {
                Errors.getInstancia().marcaErro();
                System.out.println(e);
            }
            System.out.println("Thread: " + this.nome + ". Deleting: " + x);
        }
    }

    /**
     * Método run da thread
     */
    public void run() {
        try {
            this.Deletar();
        } catch (Exception e) {
            Logger.getLogger(RiaktsDeleteThread.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
}
