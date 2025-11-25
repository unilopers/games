package com.Games.LojaGames.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "categorias")
@JacksonXmlRootElement(localName = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JacksonXmlProperty(localName = "id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @JacksonXmlProperty(localName = "nome")
    private String nome;

    @Column(columnDefinition = "TEXT")
    @JacksonXmlProperty(localName = "descricao")
    private String descricao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "criado_em", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JacksonXmlProperty(localName = "criadoEm")
    private LocalDateTime criadoEm;

    public Categoria() {
        this.criadoEm = LocalDateTime.now();
    }

    public Categoria(Long id, String nome, String descricao, LocalDateTime criadoEm) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.criadoEm = (criadoEm == null ? LocalDateTime.now() : criadoEm);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
