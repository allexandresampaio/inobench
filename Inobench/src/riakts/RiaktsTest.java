/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package riakts;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Allexandre
 */
public class RiaktsTest {

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

    public void testarInsercao() throws InterruptedException, UnknownHostException {
        List threads = new ArrayList();//lista para guardar threads em execução
        for (int i = 0; i < qtdUser; i++) {
            RiaktsInsertThread thread = new RiaktsInsertThread("user_" + i, qtdTransacoes);
            thread.start();
            threads.add(thread);
        }
        //esperando por todas as threads finalizarem pra dar continuidade
        for (Object thread : threads) {
            ((Thread) thread).join();
        }
        RiaktsFacade.getInstancia().closeRiak();
    }

    public void testarConsulta() throws InterruptedException, UnknownHostException {
        List threads = new ArrayList();//lista para guardar threads em execução
        for (int i = 0; i < qtdUser; i++) {
            RiaktsReadThread thread = new RiaktsReadThread("user_" + i, qtdTransacoes);
            thread.start();
            threads.add(thread);
        }
        //esperando por todas as threads finalizarem pra dar continuidade
        for (Object thread : threads) {
            ((Thread) thread).join();
        }
        RiaktsFacade.getInstancia().closeRiak();
    }
    
    public void testarDelete() throws InterruptedException, UnknownHostException {
        List threads = new ArrayList();//lista para guardar threads em execução
        for (int i = 0; i < qtdUser; i++) {
            RiaktsDeleteThread thread = new RiaktsDeleteThread("user_" + i, qtdTransacoes);
            thread.start();
            threads.add(thread);
        }
        //esperando por todas as threads finalizarem pra dar continuidade
        for (Object thread : threads) {
            ((Thread) thread).join();
        }
        RiaktsFacade.getInstancia().closeRiak();
    }
}