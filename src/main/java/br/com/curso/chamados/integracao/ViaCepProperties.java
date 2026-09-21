package br.com.curso.chamados.integracao;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * URL base nunca hard-coded: vem do yml e muda por profile.
 * É o upgrade natural do @Value visto no módulo 1.
 */
@ConfigurationProperties(prefix = "integracao.viacep")
public record ViaCepProperties(String baseUrl) {}
