/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nosqlbench;

/**
 *
 * @author Allexandre
 */
public class Parametros {
    
    //tipo 1 mongo, 2 couch, 3 redis, 4 riakts
    private int banco = 0;
    //tipo 1 insercao, tipo 2 consulta
    private int tipo = 0;
    private int qtdUsers = 0;
    private int qtdTransacoes = 0;

    public int getBanco() {
        return banco;
    }

    public int getTipo() {
        return tipo;
    }

    public int getQtdUsers() {
        return qtdUsers;
    }
    
    public int getQtdTransacoes() {
        return qtdTransacoes;
    }

    public void setParametros(int banco, int tipo, int qtdUsers, int qtdTransacoes) {
        this.banco = banco;
        this.tipo = tipo;
        this.qtdUsers = qtdUsers;
        this.qtdTransacoes = qtdTransacoes;
    }
    
}
