package br.com.curso.chamados.dto;

import br.com.curso.chamados.dominio.Prioridade;
import br.com.curso.chamados.dominio.StatusChamado;
import java.time.LocalDateTime;

public record ChamadoResposta(
        Long id,
        String titulo,
        StatusChamado status,
        Prioridade prioridade,
        LocalDateTime criadoEm
) {}
