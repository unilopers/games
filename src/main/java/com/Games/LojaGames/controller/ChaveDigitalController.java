package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.ChaveDigital;
import com.Games.LojaGames.repository.ChaveDigitalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chaves-digitais")
public class ChaveDigitalController {

    private final ChaveDigitalRepository repository;

    public ChaveDigitalController(ChaveDigitalRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<ChaveDigital> listarTodas() {
        return repository.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ChaveDigital> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ChaveDigital> criarChave(@RequestBody ChaveDigital chave) {
        ChaveDigital novaChave = repository.save(chave);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaChave);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ChaveDigital> atualizarChave(@PathVariable Long id, @RequestBody ChaveDigital chaveRecebida) {
        return repository.findById(id)
                .map(chaveExistente -> {
                    chaveExistente.setCodigoChave(chaveRecebida.getCodigoChave());
                    chaveExistente.setAlocado(chaveRecebida.getAlocado());
                    if (chaveRecebida.getItemPedido() != null) {
                        chaveExistente.setItemPedido(chaveRecebida.getItemPedido());
                    }
                    return ResponseEntity.ok(repository.save(chaveExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarChave(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
