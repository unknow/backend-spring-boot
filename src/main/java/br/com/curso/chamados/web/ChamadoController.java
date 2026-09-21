package br.com.curso.chamados.web;

import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.servico.ChamadoService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChamadoResposta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ChamadoResposta buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<ChamadoResposta> criar(@RequestBody NovoChamado corpo) {
        var criado = service.criar(corpo);
        var location = URI.create("/chamados/" + criado.id());
        return ResponseEntity.created(location).body(criado); // 201 + header Location
    }
}
