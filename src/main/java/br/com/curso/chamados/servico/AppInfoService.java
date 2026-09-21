package br.com.curso.chamados.servico;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppInfoService {

    @Value("${curso.nome-app}")
    private String nomeApp;

    @Value("${curso.mensagem}")
    private String mensagem;

    public String getNomeApp() {
        return nomeApp;
    }

    public String getMensagem() {
        return mensagem;
    }
}
