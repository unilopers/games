package com.Games.LojaGames.service;

import com.Games.LojaGames.model.Avaliacao;
import com.Games.LojaGames.model.Avaliacao.StatusAvaliacao;
import com.Games.LojaGames.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModeracaoAvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final List<String> palavrasProibidas;

    public ModeracaoAvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            @Value("${moderacao.palavras-proibidas:termo_ofensivo_1,termo_obsceno_1,apologia_negativa_1}") String termosConfigurados
    ) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.palavrasProibidas = Arrays.stream(termosConfigurados.split(","))
                .map(termo -> termo.toLowerCase(Locale.ROOT).trim())
                .filter(termo -> !termo.isBlank())
                .collect(Collectors.toList());
    }

    @Async
    public void processarModeracao(Long avaliacaoId) {
        Optional<Avaliacao> avaliacaoOptional = avaliacaoRepository.findById(avaliacaoId);

        if (avaliacaoOptional.isEmpty()) {
            return;
        }

        Avaliacao avaliacao = avaliacaoOptional.get();

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        String titulo = avaliacao.getTitulo() != null ? avaliacao.getTitulo().toLowerCase(Locale.ROOT) : "";
        String conteudo = avaliacao.getConteudo() != null ? avaliacao.getConteudo().toLowerCase(Locale.ROOT) : "";

        boolean contemProibida = palavrasProibidas.stream().anyMatch(p -> titulo.contains(p) || conteudo.contains(p));

        if (contemProibida) {
            avaliacao.setStatus(StatusAvaliacao.REJEITADO);
        } else {
            avaliacao.setStatus(StatusAvaliacao.APROVADO);
        }

        avaliacaoRepository.save(avaliacao);
    }
}

