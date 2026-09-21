package br.com.curso.chamados.config;

import br.com.curso.chamados.integracao.ViaCepProperties;
import java.time.Duration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(ViaCepProperties.class)
public class IntegracaoConfig {

    @Bean
    RestClient viaCepClient(RestClient.Builder builder, ViaCepProperties props) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(2)); // abrir a conexão
        factory.setReadTimeout(Duration.ofSeconds(3));    // esperar a resposta

        // Sem isso, o padrão é esperar para sempre — e ninguém escolheu isso.
        return builder.baseUrl(props.baseUrl()).requestFactory(factory).build();
    }
}
