package br.com.curso.chamados.repositorio;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.StatusChamado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Teste de repositório contra um Postgres DE VERDADE, descartável.
 *
 * Por que não H2: banco de mentira aceita SQL que o Postgres recusa, e o bug
 * aparece só em produção. Nosso schema vem de migrações escritas para Postgres.
 */
@DataJpaTest
@Testcontainers
class ChamadoRepositoryTest {

    @Container
    @ServiceConnection // o Spring aponta o datasource para o container sozinho
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @Autowired ChamadoRepository repositorio;

    @Test
    void findByStatusTrazSoOsAbertos() {
        repositorio.save(new Chamado("Aberto", "Descricao do aberto", StatusChamado.ABERTO));
        repositorio.save(new Chamado("Fechado", "Descricao do fechado", StatusChamado.FECHADO));

        var abertos = repositorio.findByStatus(StatusChamado.ABERTO, Pageable.unpaged());

        assertThat(abertos).hasSize(1);
        assertThat(abertos.getContent().get(0).getTitulo()).isEqualTo("Aberto");
    }

    @Test
    void buscaPorTituloIgnoraMaiusculas() {
        repositorio.save(new Chamado("Impressora travada", "Nao imprime", StatusChamado.ABERTO));

        assertThat(repositorio.findByTituloContainingIgnoreCase("IMPRESSORA")).hasSize(1);
    }
}
