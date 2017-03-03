/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author allexandre
 */
public class Erro {
    
    private int erros;
    private static Erro instancia = null;
    
    private Erro(){
        
    } 
    
    public static Erro getInstancia() {
        if (instancia == null) {
            instancia = new Erro();
        }
        return instancia;
    }
    
    public void marcaErro(){
        this.erros++;
    }
    
    public int getErros(){
        return this.erros;
    }
    
    public void setErro(int i){
        this.erros = i;
    }
    
    
}
