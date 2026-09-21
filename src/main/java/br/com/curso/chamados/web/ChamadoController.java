package br.com.curso.chamados.web;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    // lista fixa só para ver a serialização JSON funcionando —
    // o Bloco 2 troca por estrutura de verdade
    public record ChamadoFake(long id, String titulo) {}

    @GetMapping
    public List<ChamadoFake> listar() {
        return List.of(
                new ChamadoFake(1, "Impressora não imprime"),
                new ChamadoFake(2, "Acesso ao sistema bloqueado")
        );
    }
}
