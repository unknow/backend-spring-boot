package br.com.curso.chamados.web;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint de apoio didático: mostra o que a API enxerga do token.
 * Útil para depurar o mapeamento de roles (o "403 misterioso").
 */
@RestController
public class PerfilController {

    public record PerfilResposta(String usuario, List<String> rolesNoToken) {}

    @GetMapping("/perfil")
    @SuppressWarnings("unchecked")
    public PerfilResposta perfil(@AuthenticationPrincipal Jwt jwt) {
        var realmAccess = (java.util.Map<String, Object>) jwt.getClaims().get("realm_access");
        var roles = realmAccess == null ? List.<String>of()
                : List.copyOf((java.util.Collection<String>) realmAccess.get("roles"));
        return new PerfilResposta(jwt.getClaimAsString("preferred_username"), roles);
    }
}
