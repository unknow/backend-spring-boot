package br.com.curso.chamados.integracao;

import br.com.curso.chamados.dominio.Endereco;
import br.com.curso.chamados.excecao.CepNaoEncontrado;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * A fronteira com o mundo externo (Gateway + Anti-Corruption Layer):
 * é o único ponto que fala HTTP com o ViaCEP e o único que conhece o
 * formato deles. Para fora daqui, só trafega o nosso Endereco.
 */
@Component
public class ViaCepClient {

    private final RestClient client;

    public ViaCepClient(RestClient viaCepClient) {
        this.client = viaCepClient;
    }

    public Endereco buscarPorCep(String cep) {
        var externo = client.get()
                .uri("/ws/{cep}/json/", cep)
                .retrieve()
                .body(EnderecoExterno.class);

        // Atenção: status 200 NÃO significa sucesso nesta API.
        if (externo == null || Boolean.TRUE.equals(externo.erro())) {
            throw new CepNaoEncontrado(cep);
        }
        return traduzir(externo);
    }

    private Endereco traduzir(EnderecoExterno externo) {
        return new Endereco(externo.cep(), externo.logradouro(),
                externo.bairro(), externo.localidade(), externo.uf());
    }
}
