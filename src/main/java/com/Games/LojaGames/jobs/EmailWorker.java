package com.Games.LojaGames.jobs; 

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
//comentario para fazer pull request
@Component
public class EmailWorker {

    @Async
    public void enviarEmailBoasVindas(String email, String fabricante) {
        try {
            System.out.println("Iniciando envio de e-mail para: " + email);
            Thread.sleep(5000); // Simula o atraso do servidor de e-mail
            System.out.println("E-mail enviado com sucesso para o fabricante da plataforma " + fabricante);
        } catch (InterruptedException e) {
            System.err.println("Erro ao processar envio assíncrono.");
        }
    }
}