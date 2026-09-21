# Requests do módulo 5

Pegar um token (troque o usuário conforme o teste):

```bash
TOKEN=$(curl -s -X POST \
  http://localhost:8081/realms/curso-realm/protocol/openid-connect/token \
  -d "client_id=api-curso" \
  -d "username=ana" -d "password=123" \
  -d "grant_type=password" | jq -r .access_token)
```

Usar o token:

```bash
# sem token -> 401
curl -i http://localhost:8080/chamados

# com token -> 200
curl -i -H "Authorization: Bearer $TOKEN" http://localhost:8080/chamados

# o que a API enxerga do token
curl -s -H "Authorization: Bearer $TOKEN" http://localhost:8080/perfil

# DELETE: ana -> 403, bruno (ADMIN) -> 204
curl -i -X DELETE -H "Authorization: Bearer $TOKEN" http://localhost:8080/chamados/1

# PATCH fechar: carla (GERENTE) -> 200, de novo -> 409
curl -i -X PATCH -H "Authorization: Bearer $TOKEN" http://localhost:8080/chamados/1/fechar
```

Aberto sem token: `http://localhost:8080/swagger-ui.html` e `http://localhost:8080/health`.
