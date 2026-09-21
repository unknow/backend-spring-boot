# Módulo 8 — operação da stack

## Subir tudo

```bash
cp .env.example .env     # preencha as senhas
docker compose up -d --build
```

- API: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health
- Keycloak: http://localhost:8081

## Comandos do dia a dia

```bash
docker compose ps                 # o que está de pé
docker compose logs -f api        # acompanhar os logs da API
docker compose stop postgres      # derrubar o banco e ver o health virar DOWN
docker compose down               # derrubar tudo (o volume do banco sobrevive)
docker compose down -v            # derrubar tudo E apagar os dados
```

## Rodando só o jar

```bash
./mvnw clean package
DB_PASSWORD=curso java -jar target/chamados-0.0.1-SNAPSHOT.jar
```

## Precedência de configuração (do mais forte ao mais fraco)

1. `--server.port=9000` na linha de comando
2. `SERVER_PORT=9000` no ambiente
3. `application-<profile>.yml`
4. `application.yml`

Tradução do nome: `spring.datasource.url` → `SPRING_DATASOURCE_URL`
(maiúsculas; pontos e hífens viram underscore).
