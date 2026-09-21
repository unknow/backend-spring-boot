# Realm do curso — plano B

Se a configuração pelo console admin travar, importe este realm:

1. Suba o Keycloak: `docker compose up -d keycloak`
2. Console em http://localhost:8081 (admin / admin)
3. Menu de realms → **Create realm** → **Browse** → selecione `curso-realm.json` → Create

O realm traz:

- realm `curso-realm`
- client público `api-curso` com Direct Access Grants habilitado
- roles de realm `USER`, `ADMIN` e `GERENTE`
- usuários `ana` (USER), `bruno` (USER + ADMIN) e `carla` (USER + GERENTE),
  todos com a senha `123` não temporária

> Atenção: este realm existe só para treinamento. Senhas fracas, client público
> e usuários fixos nunca vão para um ambiente real.

Para pegar um token:

```bash
curl -X POST http://localhost:8081/realms/curso-realm/protocol/openid-connect/token \
  -d "client_id=api-curso" \
  -d "username=ana" -d "password=123" \
  -d "grant_type=password"
```
