package br.com.curso.chamados.dominio.prazo;

import br.com.curso.chamados.dominio.Prioridade;
import org.springframework.stereotype.Component;

@Component
public class PrazoBaixa implements CalculoDePrazo {

    @Override
    public Prioridade atende() {
        return Prioridade.BAIXA;
    }

    @Override
    public int prazoEmHoras() {
        return 72; // três dias
    }
}
