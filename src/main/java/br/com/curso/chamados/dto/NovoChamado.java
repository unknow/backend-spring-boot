package br.com.curso.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NovoChamado(
        @NotBlank(message = "título é obrigatório")
        @Size(max = 120)
        String titulo,

        @NotBlank(message = "descrição é obrigatória")
        @Size(min = 10, message = "descrição deve ter ao menos 10 caracteres")
        String descricao,

        String cep // opcional: quando informado, o endereço vem do serviço externo
) {}
