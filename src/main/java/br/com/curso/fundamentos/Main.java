package br.com.curso.fundamentos;

import java.util.List;

public class Main {

    // record: dados sem cerimônia — substitui classe + getters + equals + hashCode
    public record Chamado(Long id, String titulo, boolean aberto) {}

    public static void main(String[] args) {
        var chamados = List.of(
                new Chamado(1L, "Impressora não imprime", true),
                new Chamado(2L, "Acesso bloqueado", false),
                new Chamado(3L, "Sistema lento", true)
        );

        // stream: coleções sem for
        var abertos = chamados.stream()
                .filter(Chamado::aberto)
                .map(Chamado::titulo)
                .toList();

        System.out.println("Chamados abertos: " + abertos);
    }
}
