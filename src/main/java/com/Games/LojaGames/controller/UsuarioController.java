package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.Usuario;
import com.Games.LojaGames.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuariosRepository;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuariosRepository.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return usuariosRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Usuario> post(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosRepository.save(usuario));
    }

    @PutMapping( consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Usuario> put(@RequestBody Usuario usuario) {
        return usuariosRepository.findById(usuario.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(usuariosRepository.save(usuario)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuariosRepository.deleteById(id);
    }
}