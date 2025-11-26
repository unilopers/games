package com.Games.LojaGames.repository;

import com.Games.LojaGames.model.ItemListaDesejo;
import com.Games.LojaGames.model.ItemListaDesejoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemListaDesejoRepository extends JpaRepository<ItemListaDesejo, Long> {
    ItemListaDesejo findById(ItemListaDesejoId id);
}

