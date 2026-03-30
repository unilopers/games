package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.Plataforma;
import com.Games.LojaGames.repository.PlataformaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {

    @Autowired
    PlataformaRepository plataformaRepository;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<Plataforma> readAll(){
        return plataformaRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Plataforma> readById(@PathVariable Long id){
        var plataforma = plataformaRepository.findById(id);
        return plataforma.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<?> create(@RequestBody Plataforma plataforma) {
        var plat = this.plataformaRepository.findByNome(plataforma.getNome());

        if (plat != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_XML)
                    .body("<erro>Uma plataforma com esse nome já existe!!</erro>");
        }

        Plataforma newPlataforma = this.plataformaRepository.save(plataforma);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlataforma);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<Plataforma> update(@PathVariable Long id, @RequestBody Plataforma plataforma) {
        Optional<Plataforma> plataformaVerificacao = this.plataformaRepository.findById(id);

        if(plataformaVerificacao.isPresent()){
            Plataforma plataformaBanco = plataformaVerificacao.get();
            plataformaBanco.setNome(plataforma.getNome());
            plataformaBanco.setFabricante(plataforma.getFabricante());

            Plataforma plataformaSalvar = plataformaRepository.save(plataformaBanco);
            return ResponseEntity.ok(plataformaSalvar);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(plataformaRepository.existsById(id)){
            plataformaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}