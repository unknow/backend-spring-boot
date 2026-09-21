package br.com.curso.chamados.notificacao;

import br.com.curso.chamados.dominio.Chamado;

/**
 * Strategy (ou Observer, dependendo de como se olha): cada canal de
 * notificação é uma implementação. Adicionar um terceiro canal não
 * altera o ChamadoService.
 *
 * Por que vale a pena aqui: já sabemos de dois canais e o time pediu um
 * terceiro (painel). Sem isso, o service acumularia um if por canal e
 * precisaria mudar a cada novo pedido.
 */
public interface Notificador {

    void chamadoFechado(Chamado chamado);
}
