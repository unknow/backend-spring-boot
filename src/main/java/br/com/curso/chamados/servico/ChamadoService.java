package br.com.curso.chamados.servico;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.excecao.ChamadoJaFechado;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
import br.com.curso.chamados.repositorio.ChamadoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChamadoService {

    private final ChamadoRepository repositorio; // era o Map, agora é o Spring Data

    public ChamadoService(ChamadoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public ChamadoResposta criar(NovoChamado dto) {
        var chamado = new Chamado(dto.titulo(), dto.descricao(), StatusChamado.ABERTO);
        var salvo = repositorio.save(chamado);
        return paraResposta(salvo);
    }

    public ChamadoResposta buscar(Long id) {
        return paraResposta(buscarChamado(id));
    }

    public List<ChamadoResposta> listar(StatusChamado status) {
        return repositorio.findAll().stream()
                .filter(c -> status == null || c.getStatus() == status)
                .map(this::paraResposta)
                .toList();
    }

    public ChamadoResposta atualizar(Long id, NovoChamado dto) {
        var chamado = buscarChamado(id);
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        return paraResposta(repositorio.save(chamado));
    }

    public ChamadoResposta fechar(Long id) {
        var chamado = buscarChamado(id);
        if (chamado.getStatus() == StatusChamado.FECHADO) {
            throw new ChamadoJaFechado(id);
        }
        chamado.setStatus(StatusChamado.FECHADO);
        return paraResposta(repositorio.save(chamado));
    }

    public void excluir(Long id) {
        buscarChamado(id); // garante 404 para id inexistente
        repositorio.deleteById(id);
    }

    private Chamado buscarChamado(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new ChamadoNaoEncontrado(id));
    }

    private ChamadoResposta paraResposta(Chamado chamado) {
        return new ChamadoResposta(chamado.getId(), chamado.getTitulo(), chamado.getStatus());
    }
}
