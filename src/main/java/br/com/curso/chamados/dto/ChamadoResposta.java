package br.com.curso.chamados.dto;

import br.com.curso.chamados.dominio.StatusChamado;

public record ChamadoResposta(
        Long id,
        String titulo,
        StatusChamado status
) {}
