package com.Games.LojaGames.worker;

import com.Games.LojaGames.model.Avaliacao;
import com.Games.LojaGames.model.Avaliacao.StatusAvaliacao;
import com.Games.LojaGames.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class AvaliacaoModeracaoWorker {

    private final AvaliacaoRepository avaliacaoRepository;
    private final List<String> palavrasProibidas;

    public AvaliacaoModeracaoWorker(
            AvaliacaoRepository avaliacaoRepository,
            @Value("${moderacao.palavras-proibidas:termo_ofensivo_1,termo_obsceno_1,apologia_negativa_1}") String termosConfigurados
    ) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.palavrasProibidas = Arrays.stream(termosConfigurados.split(","))
                .map(termo -> termo.toLowerCase(Locale.ROOT).trim())
                .filter(termo -> !termo.isBlank())
                .collect(Collectors.toList());
    }

    @Scheduled(fixedDelayString = "${moderacao.worker.intervalo-ms:5000}")
    @Transactional
    public void processarPendentes() {
        List<Avaliacao> pendentes = avaliacaoRepository.findTop20ByStatusOrderByCriadoEmAsc(StatusAvaliacao.PENDENTE);

        for (Avaliacao avaliacao : pendentes) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            String titulo = avaliacao.getTitulo() != null ? avaliacao.getTitulo().toLowerCase(Locale.ROOT) : "";
            String conteudo = avaliacao.getConteudo() != null ? avaliacao.getConteudo().toLowerCase(Locale.ROOT) : "";

            boolean contemProibida = palavrasProibidas.stream().anyMatch(p -> titulo.contains(p) || conteudo.contains(p));
            avaliacao.setStatus(contemProibida ? StatusAvaliacao.REJEITADO : StatusAvaliacao.APROVADO);
        }
    }
}

