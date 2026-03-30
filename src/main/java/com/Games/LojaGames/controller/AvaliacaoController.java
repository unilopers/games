package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.Avaliacao;
import com.Games.LojaGames.repository.AvaliacaoRepository;
import com.Games.LojaGames.service.ModeracaoAvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ModeracaoAvaliacaoService moderacaoAvaliacaoService;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<Avaliacao>> getAll() {
        return ResponseEntity.ok(avaliacaoRepository.findAll());
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Avaliacao> getById(@PathVariable Long id) {
        return avaliacaoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Avaliacao> post(@RequestBody Avaliacao avaliacao) {
        avaliacao.setStatus(Avaliacao.StatusAvaliacao.PENDENTE);
        Avaliacao salva = avaliacaoRepository.save(avaliacao);
        moderacaoAvaliacaoService.processarModeracao(salva.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Avaliacao> put(@RequestBody Avaliacao avaliacao) {
        return avaliacaoRepository.findById(avaliacao.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(avaliacaoRepository.save(avaliacao)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        avaliacaoRepository.deleteById(id);
    }
}