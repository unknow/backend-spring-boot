package br.com.curso.chamados.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * A matriz de autorização do módulo 5, agora automatizada —
 * e sem subir o Keycloak: o jwt() cria um token de mentira já validado.
 *
 * Testamos NOSSAS regras de acesso, não a criptografia do Spring.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AutorizacaoIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @Autowired MockMvc mvc;

    @Test
    void semTokenDevolve401() throws Exception {
        mvc.perform(get("/chamados"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void comTokenListaChamados() throws Exception {
        mvc.perform(get("/chamados")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    void usuarioSemAdminNaoApagaChamado() throws Exception {
        mvc.perform(delete("/chamados/1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void healthEhPublico() throws Exception {
        mvc.perform(get("/health"))
                .andExpect(status().isOk());
    }
}
