package br.com.curso.chamados.dominio;

/**
 * O formato NOSSO. O vocabulário da casa, independente de quem fornece o dado.
 */
public record Endereco(
        String cep,
        String rua,
        String bairro,
        String cidade,
        String uf
) {}
