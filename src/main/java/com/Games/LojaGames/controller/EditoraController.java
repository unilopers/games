package com.Games.LojaGames.controller;

import com.grupo_07.games.model.Editora;
import com.grupo_07.games.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/editoras")
public class EditoraController {
    @Autowired
    EditoraRepository editoraRepository;


    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Editora> create(@RequestBody Editora editora){

        try {
            Editora newEditora = editoraRepository.save(editora);
            return new ResponseEntity<>(newEditora, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping( produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<Editora>> readAll(){
        List<Editora> editoras = editoraRepository.findAll();
        return ResponseEntity.ok(editoras);

    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Editora> update(@PathVariable Long id, @RequestBody Editora editoraRecebida) {
        Optional<Editora> editoraNoBanco = editoraRepository.findById(id);

        if (editoraNoBanco.isPresent()) {
            Editora editoraExistente = editoraNoBanco.get();

            editoraExistente.setNome(editoraRecebida.getNome());
            editoraExistente.setSite(editoraRecebida.getSite());
            editoraExistente.setPais(editoraRecebida.getPais());

            Editora editoraSalva = editoraRepository.save(editoraExistente);
            return ResponseEntity.ok(editoraSalva);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (editoraRepository.existsById(id)) {
            editoraRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

