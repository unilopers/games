package com.Games.LojaGames.repository;

import com.Games.LojaGames.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {

    Plataforma findByNome(String nome);

}