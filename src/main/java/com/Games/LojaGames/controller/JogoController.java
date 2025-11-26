package com.Games.LojaGames.controller;


import com.Games.LojaGames.model.Jogo;
import com.Games.LojaGames.model.JogoPlataforma;
import com.Games.LojaGames.model.Plataforma;
import com.Games.LojaGames.repository.JogoPlataformaRepository;
import com.Games.LojaGames.repository.JogoRepository;
import com.Games.LojaGames.repository.PlataformaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    private final JogoRepository jogoRepository;
    private final PlataformaRepository plataformaRepository;
    private final JogoPlataformaRepository jogoPlataformaRepository;

    public JogoController(JogoRepository jogoRepository, PlataformaRepository plataformaRepository, JogoPlataformaRepository jogoPlataformaRepository) {
        this.jogoRepository = jogoRepository;
        this.plataformaRepository = plataformaRepository;
        this.jogoPlataformaRepository = jogoPlataformaRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Jogo> buscarPorId(@PathVariable Long id) {
        return jogoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Jogo> criarJogo(@RequestBody Jogo jogo) {
        Jogo novoJogo = jogoRepository.save(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoJogo);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Jogo> atualizarJogo(@PathVariable Long id, @RequestBody Jogo jogoRecebido) {
        Optional<Jogo> jogoOpt = jogoRepository.findById(id);

        if (jogoOpt.isPresent()) {
            Jogo jogoExistente = jogoOpt.get();

            jogoExistente.setTitulo(jogoRecebido.getTitulo());
            jogoExistente.setSlug(jogoRecebido.getSlug());
            jogoExistente.setPreco(jogoRecebido.getPreco());
            jogoExistente.setDescricao(jogoRecebido.getDescricao());
            jogoExistente.setUrl(jogoRecebido.getUrl());
            jogoExistente.setQtndEstoque(jogoRecebido.getQtndEstoque());
            jogoExistente.setEhDigital(jogoRecebido.getEhDigital());
            jogoExistente.setDataLancamento(jogoRecebido.getDataLancamento());

            if (jogoRecebido.getEditora() != null) {
                jogoExistente.setEditora(jogoRecebido.getEditora());
            }

            Jogo jogoSalvo = jogoRepository.save(jogoExistente);
            return ResponseEntity.ok(jogoSalvo);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
        if (jogoRepository.existsById(id)) {
            jogoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/plataformas")
    public ResponseEntity<?> adicionarPlataforma(@PathVariable Long id,
                                                 @RequestBody VinculoPlataformaDTO dados) {

        Optional<Jogo> jogoOpt = jogoRepository.findById(id);
        if (jogoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jogo não encontrado");
        }

        Optional<Plataforma> platOpt = plataformaRepository.findById(dados.plataformaId);
        if (platOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plataforma não encontrada");
        }

        Jogo jogo = jogoOpt.get();
        Plataforma plataforma = platOpt.get();

        JogoPlataforma vinculo = new JogoPlataforma();
        vinculo.setJogo(jogo);
        vinculo.setPlataforma(plataforma);
        vinculo.setInfoExtra(dados.infoExtra);

        jogoPlataformaRepository.save(vinculo);

        return ResponseEntity.status(HttpStatus.CREATED).body("Plataforma vinculada com sucesso!");
    }
}

class VinculoPlataformaDTO {
    public Long plataformaId;
    public String infoExtra;
}