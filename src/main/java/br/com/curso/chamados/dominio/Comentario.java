package br.com.curso.chamados.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    private LocalDateTime criadoEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // LAZY: padrão da casa
    @JoinColumn(name = "chamado_id")
    private Chamado chamado;

    protected Comentario() {
        // exigido pelo JPA
    }

    public Comentario(String texto, Chamado chamado) {
        this.texto = texto;
        this.chamado = chamado;
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public Chamado getChamado() {
        return chamado;
    }
}
