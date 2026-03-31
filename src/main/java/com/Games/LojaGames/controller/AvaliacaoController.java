package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.Avaliacao;
import com.Games.LojaGames.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Games.LojaGames.dto.AvaliacaoRespostaDTO;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avaliacao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<AvaliacaoRespostaDTO>> getAll() {
        List<AvaliacaoRespostaDTO> lista = avaliacaoRepository.findAll()
                .stream()
                .map(AvaliacaoRespostaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AvaliacaoRespostaDTO> getById(@PathVariable long id) {
        return avaliacaoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(new AvaliacaoRespostaDTO(resposta)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AvaliacaoRespostaDTO> post(@RequestBody Avaliacao avaliacao) {
        avaliacao.setStatus(Avaliacao.StatusAvaliacao.PENDENTE);
        Avaliacao salva = avaliacaoRepository.save(avaliacao);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AvaliacaoRespostaDTO(salva));
    }

    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<AvaliacaoRespostaDTO> put(@RequestBody Avaliacao avaliacao) {
        Long id = avaliacao.getId();
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return avaliacaoRepository.findById(id)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK)
                        .body(new AvaliacaoRespostaDTO(avaliacaoRepository.save(avaliacao))))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        avaliacaoRepository.deleteById(id);
    }

    @DeleteMapping("/todas")
    public void deletarTodas() {
        avaliacaoRepository.deleteAll();
    }
}