# Por que extrair Notificador numa interface?

**Duas linhas, como pedido no exercício:**

Já existem dois canais de notificação e o time pediu um terceiro (painel);
sem a interface, o `ChamadoService` acumularia um `if` por canal e precisaria
ser alterado — e testado de novo — a cada pedido novo.

Com a lista de implementações injetada, um canal novo entra no sistema só por
existir como `@Component`, e o service continua com uma única responsabilidade:
fechar o chamado e avisar quem precisa saber.

---

## O contraexemplo, para não esquecer a lição do Bloco 3

O cálculo de prazo tinha um `if` de sete linhas, perfeitamente legível.
A refatoração para Strategy só se justificou porque as regras vão crescer
(CRITICA, horário comercial, cliente VIP). Se o prazo fosse fixo e estável,
o `if` deveria ter ficado como estava.

> Escreva simples até doer. A dor é o sinal — e ela chega sozinha.
