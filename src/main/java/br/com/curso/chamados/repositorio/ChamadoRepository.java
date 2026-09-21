package br.com.curso.chamados.repositorio;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.StatusChamado;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    // o nome do método vira SQL: select ... where status = ?
    Page<Chamado> findByStatus(StatusChamado status, Pageable pageable);

    List<Chamado> findByTituloContainingIgnoreCase(String trecho);
}
