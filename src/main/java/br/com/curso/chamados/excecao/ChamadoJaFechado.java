package br.com.curso.chamados.excecao;

public class ChamadoJaFechado extends RuntimeException {

    public ChamadoJaFechado(Long id) {
        super("Chamado " + id + " já está fechado");
    }
}
