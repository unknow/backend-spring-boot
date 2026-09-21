package br.com.curso.chamados.dominio.prazo;

import br.com.curso.chamados.dominio.Prioridade;

/**
 * Strategy: uma forma de calcular o prazo por prioridade.
 *
 * Antes existia um if/else no service. A refatoração se paga porque sabemos
 * que virão regras novas (CRITICA, horário comercial, cliente VIP) — e cada
 * uma delas passa a ser UMA CLASSE NOVA, sem tocar no que já funciona.
 */
public interface CalculoDePrazo {

    Prioridade atende();

    int prazoEmHoras();
}
