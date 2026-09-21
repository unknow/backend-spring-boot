package br.com.curso.chamados.web;

import br.com.curso.chamados.dominio.StatusChamado;
import br.com.curso.chamados.dto.ChamadoResposta;
import br.com.curso.chamados.dto.NovoChamado;
import br.com.curso.chamados.servico.ChamadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chamados", description = "Abertura e acompanhamento de chamados")
@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ChamadoResposta> listar(@RequestParam(required = false) StatusChamado status,
            Pageable pageable) {
        return service.listar(status, pageable);
    }

    @GetMapping("/busca")
    public List<ChamadoResposta> buscarPorTitulo(@RequestParam String q) {
        return service.buscarPorTitulo(q);
    }

    @GetMapping("/{id}")
    public ChamadoResposta buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @Operation(summary = "Abre um novo chamado")
    @ApiResponse(responseCode = "201", description = "Chamado aberto")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PostMapping
    public ResponseEntity<ChamadoResposta> criar(@Valid @RequestBody NovoChamado corpo) {
        var criado = service.criar(corpo);
        var location = URI.create("/chamados/" + criado.id());
        return ResponseEntity.created(location).body(criado); // 201 + header Location
    }

    @PutMapping("/{id}")
    public ChamadoResposta atualizar(@PathVariable Long id, @Valid @RequestBody NovoChamado corpo) {
        return service.atualizar(id, corpo);
    }

    @Operation(summary = "Fecha um chamado aberto")
    @ApiResponse(responseCode = "200", description = "Chamado fechado")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    @ApiResponse(responseCode = "409", description = "O chamado já estava fechado")
    @PatchMapping("/{id}/fechar")
    public ChamadoResposta fechar(@PathVariable Long id) {
        return service.fechar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
