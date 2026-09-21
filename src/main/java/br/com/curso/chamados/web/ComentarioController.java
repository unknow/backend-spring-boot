package br.com.curso.chamados.web;

import br.com.curso.chamados.dto.ComentarioResposta;
import br.com.curso.chamados.dto.NovoComentario;
import br.com.curso.chamados.servico.ComentarioService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chamados/{chamadoId}/comentarios")
public class ComentarioController {

    private final ComentarioService service;

    public ComentarioController(ComentarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ComentarioResposta> adicionar(@PathVariable Long chamadoId,
            @Valid @RequestBody NovoComentario corpo) {
        var criado = service.adicionar(chamadoId, corpo);
        var location = URI.create("/chamados/" + chamadoId + "/comentarios/" + criado.id());
        return ResponseEntity.created(location).body(criado);
    }

    @GetMapping
    public Page<ComentarioResposta> listar(@PathVariable Long chamadoId, Pageable pageable) {
        return service.listar(chamadoId, pageable);
    }
}
