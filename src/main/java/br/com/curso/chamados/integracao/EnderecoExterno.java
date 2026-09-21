package br.com.curso.chamados.integracao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * O formato DELES. Espelha o JSON do ViaCEP, inclusive a esquisitice:
 * CEP inexistente devolve HTTP 200 com {"erro": "true"}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EnderecoExterno(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf,
        Boolean erro
) {}
