package br.com.curso.chamados.config;

import br.com.curso.chamados.integracao.ViaCepProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(ViaCepProperties.class)
public class IntegracaoConfig {

    @Bean
    RestClient viaCepClient(RestClient.Builder builder, ViaCepProperties props) {
        return builder.baseUrl(props.baseUrl()).build();
    }
}
