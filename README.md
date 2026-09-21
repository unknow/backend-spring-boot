# Trilha Backend Spring Boot — Sistema de Chamados

Projeto fio-condutor da trilha de formação backend. Cada módulo evolui o mesmo
sistema; as branches marcam o estado do código ao fim de cada bloco de aula.

## Mapa de branches

| Branch | Estado do código |
| --- | --- |
| `etapa-1` | M1 · Bloco 1 — playground de Java moderno (records e streams) |
| `etapa-2` | M1 · Bloco 2 — projeto Spring Boot recém-gerado |
| `etapa-3` | M1 · Bloco 3 — DI, annotations, application.yml e profiles |
| `etapa-4` | M1 · Exercício — /health com configuração e profile dev |
| `m2-etapa-0` | Ponto de partida do módulo 2 (= etapa-4) |
| `m2-etapa-1` | M2 · Bloco 1 — controller com GET fake |
| `m2-etapa-2` | M2 · Bloco 2 — CRUD em memória, DTOs e camadas |
| `m2-etapa-3` | M2 · Bloco 3 — validação e tratamento de erros |
| `m2-etapa-4` | M2 · Exercício — PUT, DELETE, filtro e 404 |
| `m3-etapa-0` | Dever de casa do M2 — PATCH /fechar (409), validação, springdoc |
| `m3-etapa-1` | M3 · Bloco 1 — Chamado vira @Entity |
| `m3-etapa-2` | M3 · Bloco 2 — Postgres no Docker e datasource |
| `m3-etapa-3` | M3 · Bloco 3 — Spring Data e a grande troca |
| `m3-etapa-4` | M3 · Exercício — criadoEm, findByStatus e busca |
| `m4-etapa-0` | Ponto de partida do módulo 4 (= módulo 3 completo) |
| `m4-etapa-1` | M4 · Bloco 1 — Comentario @ManyToOne e endpoints aninhados |
| `m4-etapa-2` | M4 · Bloco 2 — paginação e ordenação com Pageable |
| `m4-etapa-3` | M4 · Bloco 3 — Flyway (V1, V2, validate) e @Transactional |
| `m4-etapa-4` | M4 · Exercício — migração V3 (prioridade) e comentários paginados |
| `m5-etapa-0` | Ponto de partida do módulo 5 (= módulo 4 completo) |
| `m5-etapa-1` | M5 · Bloco 2 — Keycloak no docker-compose e roteiro de tokens |
| `m5-etapa-2` | M5 · Bloco 3 — Resource Server e SecurityFilterChain |
| `m5-etapa-3` | M5 · Bloco 3 — converter de roles (o 403 misterioso) |
| `m5-etapa-4` | M5 · Exercício — role GERENTE no PATCH /fechar |
| `m7-etapa-0` | Ponto de partida do módulo 7 (módulos 1-5 completos) |
| `m7-etapa-1` | M7 · Bloco 1 — OpenAPI documentado e cadeado JWT no Swagger |
| `m7-etapa-2` | M7 · Bloco 2 — RestClient, fronteira com o ViaCEP e endereço |
| `m7-etapa-3` | M7 · Bloco 3 — timeouts, fallback com WARN e 400 de CEP |
| `m7-etapa-4` | M7 · Exercício — GET /ceps/{cep} documentado |
| `m8-etapa-0` | Ponto de partida do módulo 8 (= módulo 7 completo) |
| `m8-etapa-1` | M8 · Bloco 1 — build-info e guia de operação |
| `m8-etapa-2` | M8 · Bloco 2 — configuração por variáveis e perfil prod |
| `m8-etapa-3` | M8 · Bloco 3 — Dockerfile multi-stage e Actuator |
| `m8-etapa-4` | M8 · Exercício — stack completa no compose |
| `m9-etapa-0` | Ponto de partida do módulo 9 (a trilha inteira construída) |
| `m9-etapa-1` | M9 · Bloco 3 — if/else de prazo refatorado para Strategy |
| `m9-etapa-2` | M9 · Exercício — Notificador com lista injetada |

> As branches do módulo 6 (qualidade e testes) entram depois; o módulo 7 parte
> direto da `m5-etapa-4`.

## Como rodar

- JDK 21 e Maven (`mvn spring-boot:run`)
- A partir da `m3-etapa-2`: `docker compose up -d` antes de subir a aplicação
- Endpoints em `http://localhost:8080`

> Nota: confira em https://start.spring.io a versão estável mais recente do
> Spring Boot e ajuste o `<parent>` do pom.xml se desejar.
