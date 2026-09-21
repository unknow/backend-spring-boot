package br.com.curso.chamados;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Teste de fluxo (ponta a ponta na API): sobe a aplicação inteira.
 * Poucos assim — é o mais lento e o mais frágil. Cobre o caminho crítico.
 *
 * Convenção: nome termina em IT, para separar dos testes rápidos.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class FluxoDeChamadoIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @Autowired MockMvc mvc;

    @Test
    void abreConsultaEFechaUmChamado() throws Exception {
        var corpo = mvc.perform(post("/chamados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Impressora\",\"descricao\":\"Nao imprime nada\"}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var id = com.jayway.jsonpath.JsonPath.read(corpo, "$.id").toString();

        mvc.perform(get("/chamados/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ABERTO"));

        mvc.perform(patch("/chamados/" + id + "/fechar"))
                .andExpect(status().isOk());

        // fechar de novo: o 409 do módulo 2, sobre o banco do módulo 3,
        // com a transação do módulo 4 — tudo provado em um teste.
        mvc.perform(patch("/chamados/" + id + "/fechar"))
                .andExpect(status().isConflict());
    }
}
