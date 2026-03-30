package com.Games.LojaGames.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "itens_lista_desejos")
@JacksonXmlRootElement(localName = "itemListaDesejo")
public class ItemListaDesejo {

    @EmbeddedId
    private ItemListaDesejoId id = new ItemListaDesejoId();

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("jogoId")
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @CreationTimestamp
    @Column(name = "adicionado_em", updatable = false)
    private LocalDateTime adicionadoEm;

    public ItemListaDesejoId getId() { return id; }
    public void setId(ItemListaDesejoId id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Jogo getJogo() { return jogo; }
    public void setJogo(Jogo jogo) { this.jogo = jogo; }
    public LocalDateTime getAdicionadoEm() { return adicionadoEm; }
    public void setAdicionadoEm(LocalDateTime adicionadoEm) { this.adicionadoEm = adicionadoEm; }
}
