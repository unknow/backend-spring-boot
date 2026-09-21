package br.com.curso.chamados.web;

import br.com.curso.chamados.dominio.Endereco;
import br.com.curso.chamados.integracao.ViaCepClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CEPs", description = "Consulta de endereço por CEP")
@RestController
public class CepController {

    private final ViaCepClient viaCep;

    public CepController(ViaCepClient viaCep) {
        this.viaCep = viaCep;
    }

    @Operation(summary = "Busca o endereço de um CEP no serviço externo")
    @ApiResponse(responseCode = "200", description = "Endereço encontrado")
    @ApiResponse(responseCode = "400", description = "CEP inexistente ou malformado")
    @GetMapping("/ceps/{cep}")
    public Endereco buscar(@PathVariable String cep) {
        return viaCep.buscarPorCep(cep);
    }
}
