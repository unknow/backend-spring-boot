package br.com.curso.chamados.servico;

import br.com.curso.chamados.dominio.Comentario;
import br.com.curso.chamados.dto.ComentarioResposta;
import br.com.curso.chamados.dto.NovoComentario;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
import br.com.curso.chamados.repositorio.ChamadoRepository;
import br.com.curso.chamados.repositorio.ComentarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarios;
    private final ChamadoRepository chamados;

    public ComentarioService(ComentarioRepository comentarios, ChamadoRepository chamados) {
        this.comentarios = comentarios;
        this.chamados = chamados;
    }

    public ComentarioResposta adicionar(Long chamadoId, NovoComentario dto) {
        var chamado = chamados.findById(chamadoId)
                .orElseThrow(() -> new ChamadoNaoEncontrado(chamadoId));
        var salvo = comentarios.save(new Comentario(dto.texto(), chamado));
        return paraResposta(salvo);
    }

    public List<ComentarioResposta> listar(Long chamadoId) {
        if (!chamados.existsById(chamadoId)) {
            throw new ChamadoNaoEncontrado(chamadoId);
        }
        return comentarios.findByChamadoId(chamadoId).stream()
                .map(this::paraResposta)
                .toList();
    }

    private ComentarioResposta paraResposta(Comentario comentario) {
        return new ComentarioResposta(comentario.getId(), comentario.getTexto(),
                comentario.getCriadoEm());
    }
}
