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

## Como rodar

- JDK 21 e Maven (`mvn spring-boot:run`)
- A partir da `m3-etapa-2`: `docker compose up -d` antes de subir a aplicação
- Endpoints em `http://localhost:8080`

> Nota: confira em https://start.spring.io a versão estável mais recente do
> Spring Boot e ajuste o `<parent>` do pom.xml se desejar.
