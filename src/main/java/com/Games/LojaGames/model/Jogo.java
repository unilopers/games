package com.Games.LojaGames.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "jogos")
@JacksonXmlRootElement(localName = "jogo")
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "slug", unique = true, nullable = false)
    private String slug;

    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;

    private Date dataLancamento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(length = 600)
    private String url;

    private int qtndEstoque;

    private Boolean ehDigital;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<JogoPlataforma> plataformaDoJogo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getQtndEstoque() {
        return qtndEstoque;
    }

    public void setQtndEstoque(int qtndEstoque) {
        this.qtndEstoque = qtndEstoque;
    }

    public Boolean getEhDigital() {
        return ehDigital;
    }

    public void setEhDigital(Boolean ehDigital) {
        this.ehDigital = ehDigital;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public List<JogoPlataforma> getPlataformaDoJogo() {
        return plataformaDoJogo;
    }

    public void setPlataformaDoJogo(List<JogoPlataforma> plataformaDoJogo) {
        this.plataformaDoJogo = plataformaDoJogo;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

}

