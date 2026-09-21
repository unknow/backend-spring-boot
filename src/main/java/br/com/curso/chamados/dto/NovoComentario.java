package br.com.curso.chamados.dto;

import jakarta.validation.constraints.NotBlank;

public record NovoComentario(
        @NotBlank(message = "texto é obrigatório")
        String texto
) {}
