package com.Games.LojaGames.model;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JogoPlataformaId implements Serializable {

    private Long jogoId;
    private Long plataformaId;

    public JogoPlataformaId() {}

    public JogoPlataformaId(Long jogoId, Long plataformaId) {
        this.jogoId = jogoId;
        this.plataformaId = plataformaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogoPlataformaId that = (JogoPlataformaId) o;
        return Objects.equals(jogoId, that.jogoId) && Objects.equals(plataformaId, that.plataformaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jogoId, plataformaId);
    }
}