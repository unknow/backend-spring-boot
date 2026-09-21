package br.com.curso.chamados.excecao;

public class ChamadoNaoEncontrado extends RuntimeException {

    public ChamadoNaoEncontrado(Long id) {
        super("Chamado " + id + " não encontrado");
    }
}
