package br.com.curso.chamados.dto;

import java.time.LocalDateTime;

public record ComentarioResposta(
        Long id,
        String texto,
        LocalDateTime criadoEm
) {}
