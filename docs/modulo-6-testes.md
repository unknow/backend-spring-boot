# Módulo 6 — rodando os testes

```bash
./mvnw test                  # tudo
./mvnw test -Dtest='*Test'   # só os rápidos (unidade e web)
./mvnw verify                # inclui os *IT (precisam de Docker)
```

Relatório de cobertura (JaCoCo), depois de `./mvnw test`:

```
target/site/jacoco/index.html
```

## O que cada teste do projeto prova

| Teste | Tipo | Prova |
| --- | --- | --- |
| `ChamadoServiceTest` | unidade | Regras de negócio: status inicial, 409, 404 |
| `ComentarioServiceTest` | unidade | Comentário exige chamado existente |
| `ChamadoControllerTest` | web (`@WebMvcTest`) | Contrato HTTP: 201, 400 da validação, 404 do advice |
| `ChamadoRepositoryTest` | dados (`@DataJpaTest`) | Query methods contra Postgres real |
| `FluxoDeChamadoIT` | fluxo (`@SpringBootTest`) | Caminho crítico: abrir → consultar → fechar → 409 |
| `AutorizacaoIT` | fluxo + segurança | Matriz 401 / 403 / 200 e o /health público |

## Lembrete do Bloco 3

Cobertura mede execução, não qualidade. A pergunta certa não é "quanto por
cento?", é **"se eu quebrar isso, algum teste fica vermelho?"**.
