package com.Games.LojaGames.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chaves_digitais")
@JacksonXmlRootElement(localName = "chaveDigital")
public class ChaveDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    @ManyToOne
    @JoinColumn(name = "item_pedido_id")
    private ItemPedido itemPedido;

    @Column(name = "codigo_chave", nullable = false)
    private String codigoChave;

    @Column(nullable = false)
    private Boolean alocado = false;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Jogo getJogo() { return jogo; }
    public void setJogo(Jogo jogo) { this.jogo = jogo; }
    public ItemPedido getItemPedido() { return itemPedido; }
    public void setItemPedido(ItemPedido itemPedido) { this.itemPedido = itemPedido; }
    public String getCodigoChave() { return codigoChave; }
    public void setCodigoChave(String codigoChave) { this.codigoChave = codigoChave; }
    public Boolean getAlocado() { return alocado; }
    public void setAlocado(Boolean alocado) { this.alocado = alocado; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
}
