package com.Games.LojaGames.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jogo_plataformas")
public class JogoPlataforma {

    @EmbeddedId
    private JogoPlataformaId id = new JogoPlataformaId();

    @ManyToOne
    @MapsId("jogoId")
    @JoinColumn(name="jogo_id")
    private Jogo jogo;

    @ManyToOne
    @MapsId("plataformaId")
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;

    private String infoExtra;

    public JogoPlataformaId getId() {
        return id;
    }

    public void setId(JogoPlataformaId id) {
        this.id = id;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public String getInfoExtra() {
        return infoExtra;
    }

    public void setInfoExtra(String infoExtra) {
        this.infoExtra = infoExtra;
    }
}
