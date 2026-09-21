package br.com.curso.chamados.web;

import br.com.curso.chamados.servico.AppInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OlaController {

    private final AppInfoService appInfo;

    public OlaController(AppInfoService appInfo) {
        this.appInfo = appInfo;
    }

    @GetMapping("/ola")
    public String ola() {
        return appInfo.getNomeApp() + " no ar! (" + appInfo.getMensagem() + ")";
    }
}
