package br.com.curso.chamados.repositorio;

import br.com.curso.chamados.dominio.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByChamadoId(Long chamadoId);
}
