package br.com.curso.chamados.repositorio;

import br.com.curso.chamados.dominio.Chamado;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class ChamadoRepositorioMemoria {

    private final Map<Long, Chamado> dados = new ConcurrentHashMap<>();
    private final AtomicLong sequencia = new AtomicLong(1);

    public Chamado salvar(Chamado chamado) {
        if (chamado.getId() == null) {
            chamado.setId(sequencia.getAndIncrement());
        }
        dados.put(chamado.getId(), chamado);
        return chamado;
    }

    public Optional<Chamado> buscarPorId(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    public List<Chamado> listar() {
        return List.copyOf(dados.values());
    }

    public void excluir(Long id) {
        dados.remove(id);
    }
}
