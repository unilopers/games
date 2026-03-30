package com.Games.LojaGames.worker;

import com.Games.LojaGames.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class EditoraValidationWorker {

    @Autowired
    private EditoraRepository editoraRepository;

    @Async("validadorSiteExecutor")
    public void validarSite(Long editoraId, String url) {
        if (url == null || url.isBlank()) return;

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                editoraRepository.findById(editoraId).ifPresent(editora -> {
                    editora.setSiteValidado(true);
                    editoraRepository.save(editora);
                    System.out.println("Site da editora " + editoraId + " validado com sucesso!");
                });
            }
        } catch (Exception e) {
            System.err.println("Erro ao validar o site para editora " + editoraId + ": " + e.getMessage());
        }
    }
}