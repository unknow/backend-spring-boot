package br.com.curso.chamados.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.excecao.ChamadoNaoEncontrado;
import br.com.curso.chamados.servico.ChamadoService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Teste da camada web: sobe apenas o controller (e o advice de erros).
 * Aqui se prova o CONTRATO HTTP — rota, status, JSON e validação.
 */
@WebMvcTest(ChamadoController.class)
class ChamadoControllerTest {

    @Autowired MockMvc mvc;

    @MockitoBean ChamadoService service;

    @Test
    void postValidoDevolve201() throws Exception {
        when(service.criar(any(NovoChamado.class))).thenReturn(
                new ChamadoResposta(1L, "Impressora", StatusChamado.ABERTO, null,
                        LocalDateTime.now()));

        mvc.perform(post("/chamados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Impressora\",\"descricao\":\"Nao imprime nada\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void postSemTituloDevolve400() throws Exception {
        mvc.perform(post("/chamados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"\",\"descricao\":\"Nao imprime nada\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void idInexistenteDevolve404() throws Exception {
        when(service.buscar(99L)).thenThrow(new ChamadoNaoEncontrado(99L));

        // prova que o @RestControllerAdvice do módulo 2 está realmente ligado
        mvc.perform(get("/chamados/99"))
                .andExpect(status().isNotFound());
    }
}
