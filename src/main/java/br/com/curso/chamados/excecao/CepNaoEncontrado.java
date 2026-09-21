package br.com.curso.chamados.excecao;

public class CepNaoEncontrado extends RuntimeException {

    public CepNaoEncontrado(String cep) {
        super("CEP " + cep + " não encontrado");
    }
}
