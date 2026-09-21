package br.com.curso.chamados.servico;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.repositorio.ChamadoRepositorioMemoria;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChamadoService {

    private final ChamadoRepositorioMemoria repositorio;

    public ChamadoService(ChamadoRepositorioMemoria repositorio) {
        this.repositorio = repositorio;
    }

    public ChamadoResposta criar(NovoChamado dto) {
        var chamado = new Chamado(dto.titulo(), dto.descricao(), StatusChamado.ABERTO);
        var salvo = repositorio.salvar(chamado);
        return paraResposta(salvo);
    }

    public ChamadoResposta buscar(Long id) {
        return repositorio.buscarPorId(id)
                .map(this::paraResposta)
                .orElseThrow(); // por enquanto estoura 500 — o Bloco 3 conserta
    }

    public List<ChamadoResposta> listar() {
        return repositorio.listar().stream()
                .map(this::paraResposta)
                .toList();
    }

    private ChamadoResposta paraResposta(Chamado chamado) {
        return new ChamadoResposta(chamado.getId(), chamado.getTitulo(), chamado.getStatus());
    }
}
