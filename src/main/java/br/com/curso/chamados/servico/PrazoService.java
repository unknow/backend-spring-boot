package br.com.curso.chamados.servico;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.Prioridade;
import br.com.curso.chamados.dominio.prazo.CalculoDePrazo;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PrazoService {

    private final Map<Prioridade, CalculoDePrazo> regras;

    /**
     * O Spring injeta TODAS as implementações de CalculoDePrazo que encontrar.
     * Uma regra nova entra no sistema só por existir — esta classe não muda.
     */
    public PrazoService(List<CalculoDePrazo> estrategias) {
        this.regras = estrategias.stream()
                .collect(Collectors.toMap(CalculoDePrazo::atende, Function.identity()));
    }

    public int prazoEmHoras(Chamado chamado) {
        var regra = regras.get(chamado.getPrioridade());
        if (regra == null) {
            throw new IllegalStateException(
                    "Sem regra de prazo para a prioridade " + chamado.getPrioridade());
        }
        return regra.prazoEmHoras();
    }
}
