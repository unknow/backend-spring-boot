package br.com.curso.chamados.notificacao;

import br.com.curso.chamados.dominio.Chamado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmail implements Notificador {

    private static final Logger log = LoggerFactory.getLogger(NotificadorEmail.class);

    @Override
    public void chamadoFechado(Chamado chamado) {
        // Implementação de treinamento: só registra no log.
        log.info("[e-mail] chamado {} fechado: {}", chamado.getId(), chamado.getTitulo());
    }
}
