package br.com.curso.chamados.servico;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
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
        return paraResposta(buscarChamado(id));
    }

    public List<ChamadoResposta> listar(StatusChamado status) {
        return repositorio.listar().stream()
                .filter(c -> status == null || c.getStatus() == status)
                .map(this::paraResposta)
                .toList();
    }

    public ChamadoResposta atualizar(Long id, NovoChamado dto) {
        var chamado = buscarChamado(id);
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        return paraResposta(repositorio.salvar(chamado));
    }

    public void excluir(Long id) {
        buscarChamado(id); // garante 404 para id inexistente
        repositorio.excluir(id);
    }

    private Chamado buscarChamado(Long id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new ChamadoNaoEncontrado(id));
    }

    private ChamadoResposta paraResposta(Chamado chamado) {
        return new ChamadoResposta(chamado.getId(), chamado.getTitulo(), chamado.getStatus());
    }
}
