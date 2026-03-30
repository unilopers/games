package com.Games.LojaGames.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemListaDesejoId implements Serializable {

    private Long usuarioId;
    private Long jogoId;

    public ItemListaDesejoId() {}

    public ItemListaDesejoId(Long usuarioId, Long jogoId) {
        this.usuarioId = usuarioId;
        this.jogoId = jogoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemListaDesejoId that = (ItemListaDesejoId) o;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(jogoId, that.jogoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, jogoId);
    }
}
