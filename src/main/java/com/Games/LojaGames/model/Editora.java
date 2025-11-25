package com.Games.LojaGames.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "editoras")
@JacksonXmlRootElement(localName = "editora")
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "editora_id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;
    private String site;
    private String pais;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    public Editora(Long id, String nome, String site, String pais, LocalDateTime criadoEm) {
        this.id = id;
        this.nome = nome;
        this.site = site;
        this.pais = pais;
        this.criadoEm = criadoEm;
    }

    public Editora() {
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
