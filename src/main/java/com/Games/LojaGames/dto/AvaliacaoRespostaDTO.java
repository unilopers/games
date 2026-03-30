package com.Games.LojaGames.dto;

import com.Games.LojaGames.model.Avaliacao;

public record AvaliacaoRespostaDTO(
        Long id,
        String titulo,
        String conteudo,
        int nota,
        String status,
        Long usuarioId,
        Long jogoId
) {
    public AvaliacaoRespostaDTO(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getTitulo(),
                avaliacao.getConteudo(),
                avaliacao.getNota(),
                avaliacao.getStatus() != null ? avaliacao.getStatus().name() : null,
                avaliacao.getUsuario() != null ? avaliacao.getUsuario().getId() : null,
                avaliacao.getJogo() != null ? avaliacao.getJogo().getId() : null
        );
    }
}
