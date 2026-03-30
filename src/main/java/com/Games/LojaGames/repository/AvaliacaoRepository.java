package com.Games.LojaGames.repository;

import com.Games.LojaGames.model.Avaliacao;
import com.Games.LojaGames.model.Avaliacao.StatusAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findTop20ByStatusOrderByCriadoEmAsc(StatusAvaliacao status);
}
