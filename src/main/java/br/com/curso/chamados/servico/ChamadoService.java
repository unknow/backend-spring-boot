package br.com.curso.chamados.servico;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.Comentario;
import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.excecao.CepNaoEncontrado;
import br.com.curso.chamados.excecao.ChamadoJaFechado;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
import br.com.curso.chamados.integracao.ViaCepClient;
import br.com.curso.chamados.notificacao.Notificador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.curso.chamados.repositorio.ChamadoRepository;
import br.com.curso.chamados.repositorio.ComentarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

@Service
public class ChamadoService {

    private static final Logger log = LoggerFactory.getLogger(ChamadoService.class);

    private final ChamadoRepository repositorio;
    private final ComentarioRepository comentarios;
    private final ViaCepClient enderecos;
    private final List<Notificador> notificadores; // todas as implementações

    public ChamadoService(ChamadoRepository repositorio, ComentarioRepository comentarios,
            ViaCepClient enderecos, List<Notificador> notificadores) {
        this.repositorio = repositorio;
        this.comentarios = comentarios;
        this.enderecos = enderecos;
        this.notificadores = notificadores;
    }

    @Transactional
    public ChamadoResposta criar(NovoChamado dto) {
        var chamado = new Chamado(dto.titulo(), dto.descricao(), StatusChamado.ABERTO);
        chamado.setCriadoEm(LocalDateTime.now());

        if (dto.cep() != null && !dto.cep().isBlank()) {
            try {
                chamado.definirEndereco(enderecos.buscarPorCep(dto.cep()));
            } catch (CepNaoEncontrado e) {
                throw e; // erro do usuário: vira 400 no advice
            } catch (RestClientException e) {
                // Serviço fora do ar ou lento: endereço é conveniência, não requisito.
                // Decisão de negócio: o chamado abre mesmo assim (degradação).
                log.warn("ViaCEP indisponível cep={} - chamado segue sem endereço",
                        dto.cep(), e);
            }
        }

        return paraResposta(repositorio.save(chamado));
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

        // Canal novo? Basta existir como @Component: esta classe não muda.
        notificadores.forEach(n -> n.chamadoFechado(chamado));

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
