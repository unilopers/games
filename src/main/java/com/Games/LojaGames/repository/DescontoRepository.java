package com.Games.LojaGames.repository;

import com.Games.LojaGames.model.Desconto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescontoRepository extends JpaRepository<Desconto, Long> {
}