package br.com.curso.chamados.servico;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.curso.chamados.dto.NovoComentario;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
import br.com.curso.chamados.repositorio.ChamadoRepository;
import br.com.curso.chamados.repositorio.ComentarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComentarioServiceTest {

    @Mock ComentarioRepository comentarios;
    @Mock ChamadoRepository chamados;

    @InjectMocks ComentarioService service;

    @Test
    void naoComentaEmChamadoInexistente() {
        when(chamados.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.adicionar(99L, new NovoComentario("texto")))
                .isInstanceOf(ChamadoNaoEncontrado.class);
    }
}
