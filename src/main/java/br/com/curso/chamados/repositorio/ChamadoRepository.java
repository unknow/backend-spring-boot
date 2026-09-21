package br.com.curso.chamados.repositorio;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.StatusChamado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    // o nome do método vira SQL: select ... where status = ?
    List<Chamado> findByStatus(StatusChamado status);
}
