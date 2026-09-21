package br.com.curso.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovoChamado(
        @NotBlank(message = "título é obrigatório")
        @Size(max = 120)
        String titulo,

        @NotBlank(message = "descrição é obrigatória")
        String descricao
) {}
