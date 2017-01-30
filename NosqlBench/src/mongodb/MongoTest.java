/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongodb;

/**
 *
 * @author Allexandre
 */
public class MongoTest {

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

    public void testarInsercao() {
        for (int i = 0; i < qtdUser; i++){
            new MongoThreadInsercao("user_"+i, qtdTransacoes).start();
        }
    }

    public void testarConsulta() {
        for (int i = 0; i < qtdUser; i++){
            new MongoThreadConsulta("user_"+i, qtdTransacoes).start();
        }
    }

}
