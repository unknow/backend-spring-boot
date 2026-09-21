package br.com.curso.chamados.dominio.prazo;

import br.com.curso.chamados.dominio.Prioridade;
import org.springframework.stereotype.Component;

@Component
public class PrazoMedia implements CalculoDePrazo {

    @Override
    public Prioridade atende() {
        return Prioridade.MEDIA;
    }

    @Override
    public int prazoEmHoras() {
        return 24; // um dia útil
    }
}
