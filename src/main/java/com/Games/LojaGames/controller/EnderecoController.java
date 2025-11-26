package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.Endereco;
import com.Games.LojaGames.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecosRepository;

    @GetMapping
    public ResponseEntity<List<Endereco>> getAll() {
        return ResponseEntity.ok(enderecosRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getById(@PathVariable Long id) {
        return enderecosRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Endereco> post(@RequestBody Endereco endereco) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecosRepository.save(endereco));
    }

    @PutMapping
    public ResponseEntity<Endereco> put(@RequestBody Endereco endereco) {
        return enderecosRepository.findById(endereco.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(enderecosRepository.save(endereco)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        enderecosRepository.deleteById(id);
    }
}
