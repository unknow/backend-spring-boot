package br.com.curso.chamados.repositorio;

import br.com.curso.chamados.dominio.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    Page<Comentario> findByChamadoId(Long chamadoId, Pageable pageable);
}
