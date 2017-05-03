/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 * Classe para marcar a ocorrência de erros de execução
 * @author allexandre
 */
public class Errors {
    
    private int erros;
    private static Errors instancia = null;
    
    private Errors(){
        
    } 
    
    public static Errors getInstancia() {
        if (instancia == null) {
            instancia = new Errors();
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
