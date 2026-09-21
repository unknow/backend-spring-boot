package br.com.curso.chamados.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/health").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/chamados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/chamados/*/fechar").hasRole("GERENTE")
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable()) // API stateless com token
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(o -> o.jwt(jwt -> jwt
                        .jwtAuthenticationConverter(conversorDeRoles())));
        return http.build();
    }

    /**
     * O Spring, por padrão, só olha o claim "scope" e cria authorities SCOPE_*.
     * As roles do Keycloak vivem em realm_access.roles — este converter as extrai
     * e prefixa com ROLE_, que é o que hasRole("ADMIN") espera encontrar.
     */
    private Converter<Jwt, AbstractAuthenticationToken> conversorDeRoles() {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(SecurityConfig::extrairRoles);
        return converter;
    }

    @SuppressWarnings("unchecked")
    private static Collection<GrantedAuthority> extrairRoles(Jwt jwt) {
        var realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) {
            return List.of();
        }
        var roles = (Collection<String>) realmAccess.get("roles");
        return roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }
}
