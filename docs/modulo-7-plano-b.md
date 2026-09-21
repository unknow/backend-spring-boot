# Módulo 7 — plano B quando a saída para a internet é bloqueada

Se a rede corporativa impedir a chamada ao ViaCEP, suba um serviço de mentira
local e rode a aplicação com o profile `local-integracao`.

Opção rápida, com Python (já vem instalado na maioria das máquinas):

```bash
mkdir -p /tmp/fakecep/ws/01001000/json
cat > /tmp/fakecep/ws/01001000/json/index.html <<'JSON'
{"cep":"01001-000","logradouro":"Praça da Sé","bairro":"Sé",
 "localidade":"São Paulo","uf":"SP"}
JSON
cd /tmp/fakecep && python3 -m http.server 9090
```

Depois:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local-integracao
```

Para exercitar o Bloco 3 (falhas), basta derrubar esse servidor: a aplicação
deve continuar abrindo chamados, sem endereço e com WARN no log.
