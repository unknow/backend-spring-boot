package br.com.curso.chamados.servico;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.Comentario;
import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.excecao.ChamadoJaFechado;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
import br.com.curso.chamados.repositorio.ChamadoRepository;
import br.com.curso.chamados.repositorio.ComentarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChamadoService {

    private final ChamadoRepository repositorio;
    private final ComentarioRepository comentarios;

    public ChamadoService(ChamadoRepository repositorio, ComentarioRepository comentarios) {
        this.repositorio = repositorio;
        this.comentarios = comentarios;
    }

    public ChamadoResposta criar(NovoChamado dto) {
        var chamado = new Chamado(dto.titulo(), dto.descricao(), StatusChamado.ABERTO);
        chamado.setCriadoEm(LocalDateTime.now());
        var salvo = repositorio.save(chamado);
        return paraResposta(salvo);
    }

    public ChamadoResposta buscar(Long id) {
        return paraResposta(buscarChamado(id));
    }

    public Page<ChamadoResposta> listar(StatusChamado status, Pageable pageable) {
        var pagina = (status == null)
                ? repositorio.findAll(pageable)
                : repositorio.findByStatus(status, pageable);
        return pagina.map(this::paraResposta); // entidade -> DTO mantendo os metadados
    }

    public List<ChamadoResposta> buscarPorTitulo(String trecho) {
        return repositorio.findByTituloContainingIgnoreCase(trecho).stream()
                .map(this::paraResposta)
                .toList();
    }

    public ChamadoResposta atualizar(Long id, NovoChamado dto) {
        var chamado = buscarChamado(id);
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        return paraResposta(repositorio.save(chamado));
    }

    @Transactional // duas escritas, uma transação: tudo ou nada
    public ChamadoResposta fechar(Long id) {
        var chamado = buscarChamado(id);
        if (chamado.getStatus() == StatusChamado.FECHADO) {
            throw new ChamadoJaFechado(id);
        }
        chamado.setStatus(StatusChamado.FECHADO); // dirty checking persiste no commit
        comentarios.save(new Comentario("Chamado encerrado", chamado));
        return paraResposta(chamado);
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
        return new ChamadoResposta(chamado.getId(), chamado.getTitulo(),
                chamado.getStatus(), chamado.getPrioridade(), chamado.getCriadoEm());
    }
}
