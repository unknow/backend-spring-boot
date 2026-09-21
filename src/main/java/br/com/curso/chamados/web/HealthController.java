package br.com.curso.chamados.web;

import br.com.curso.chamados.servico.AppInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    public record HealthResposta(String app, String status) {}

    private final AppInfoService appInfo;

    public HealthController(AppInfoService appInfo) {
        this.appInfo = appInfo;
    }

    @GetMapping("/health")
    public HealthResposta health() {
        return new HealthResposta(appInfo.getNomeApp(), "OK");
    }
}
