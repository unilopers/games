package com.Games.LojaGames.repository;

import com.grupo_07.games.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    Plataforma findByNome(String nome);

}