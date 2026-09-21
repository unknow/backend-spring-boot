package br.com.curso.chamados.servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.curso.chamados.dominio.Chamado;
import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.excecao.ChamadoJaFechado;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
import br.com.curso.chamados.repositorio.ChamadoRepository;
import br.com.curso.chamados.repositorio.ComentarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Teste de unidade: rápido, sem banco, sem Spring.
 *
 * Só é possível porque o service pede as dependências no construtor
 * (decisão do módulo 1) — aqui entregamos dublês em vez dos objetos reais.
 */
@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {

    @Mock ChamadoRepository repositorio;
    @Mock ComentarioRepository comentarios;

    @InjectMocks ChamadoService service;

    @Test
    void chamadoNasceAberto() {
        // dado
        var novo = new NovoChamado("Impressora", "Nao imprime nada");
        when(repositorio.save(any(Chamado.class))).thenAnswer(i -> i.getArgument(0));

        // quando
        var resposta = service.criar(novo);

        // então
        assertThat(resposta.status()).isEqualTo(StatusChamado.ABERTO);
    }

    @Test
    void naoFechaChamadoJaFechado() {
        var chamado = new Chamado("Titulo", "Descricao", StatusChamado.FECHADO);
        when(repositorio.findById(1L)).thenReturn(Optional.of(chamado));

        assertThatThrownBy(() -> service.fechar(1L))
                .isInstanceOf(ChamadoJaFechado.class);
    }

    @Test
    void buscarIdInexistenteLancaNaoEncontrado() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscar(99L))
                .isInstanceOf(ChamadoNaoEncontrado.class);
    }
}
