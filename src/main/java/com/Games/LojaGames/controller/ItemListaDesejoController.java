package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.ItemListaDesejo;
import com.Games.LojaGames.model.ItemListaDesejoId;
import com.Games.LojaGames.repository.ItemListaDesejoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lista-desejos")
public class ItemListaDesejoController {

    private final ItemListaDesejoRepository repository;

    public ItemListaDesejoController(ItemListaDesejoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<ItemListaDesejo> listarTodos() {
        return repository.findAll();
    }

    @GetMapping(value = "/usuario/{usuarioId}/jogo/{jogoId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ItemListaDesejo> buscarPorId(@PathVariable Long usuarioId, @PathVariable Long jogoId) {
        ItemListaDesejoId id = new ItemListaDesejoId(usuarioId, jogoId);

        var item = repository.findById(id);

        if (item != null) {
            return ResponseEntity.ok(item);
        }

        return ResponseEntity.notFound().build();
    }


    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ItemListaDesejo> adicionarALista(@RequestBody ItemListaDesejo item) {
        if (item.getId() == null && item.getUsuario() != null && item.getJogo() != null) {
            item.setId(new ItemListaDesejoId(item.getUsuario().getId(), item.getJogo().getId()));
        }

        ItemListaDesejo salvo = repository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @DeleteMapping("/usuario/{usuarioId}/jogo/{jogoId}")
    public ResponseEntity<Void> removerDaLista(@PathVariable Long usuarioId, @PathVariable Long jogoId) {
        ItemListaDesejoId id = new ItemListaDesejoId(usuarioId, jogoId);

        var item = repository.findById(id);

        if (item != null) {
            repository.delete(item);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
