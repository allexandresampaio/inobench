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
class MongoThreadConsulta extends Thread {

    private String host = "localhost";
    private String port = "27017";
    private String dbName = "teste";
    private int i = 0;

    String nome;
    int qtdTransacoes;	

    /**
     * Construtor da classe.
     */
    public MongoThreadConsulta(String nome, int qtdTransacoes) {
        /* chamando o construtor de Thread passando o nome da thread como parâmetro */
        super(nome);
        this.qtdTransacoes = qtdTransacoes;
        this.nome = nome;
    }

    public void TestarConsulta() {
        for (int x = 0; x < qtdTransacoes; x++) {
            
            //FAZER LEITURA
            //FachadaMongo.getInstancia().insert(host, port, dbName, documentos.get(i));
            //verifica se i chegou no fim da amostra
        }
    }

    /**
     * Método run da thread
     */
    public void run() {
        this.TestarConsulta();
    }
}
