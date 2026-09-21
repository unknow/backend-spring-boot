package br.com.curso.chamados.dominio.prazo;

import br.com.curso.chamados.dominio.Prioridade;
import org.springframework.stereotype.Component;

@Component
public class PrazoAlta implements CalculoDePrazo {

    @Override
    public Prioridade atende() {
        return Prioridade.ALTA;
    }

    @Override
    public int prazoEmHoras() {
        return 4; // meio turno
    }
}
