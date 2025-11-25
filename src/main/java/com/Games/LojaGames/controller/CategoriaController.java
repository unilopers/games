package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.Categoria;
import com.Games.LojaGames.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/view")
    public String viewCategorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categorias";
    }

    @PostMapping(
            produces = MediaType.APPLICATION_XML_VALUE,
            consumes = MediaType.APPLICATION_XML_VALUE
    )
    @ResponseBody
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria) {
        try {
            Categoria newCategoria = categoriaRepository.save(categoria);
            return new ResponseEntity<>(newCategoria, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<List<Categoria>> readAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<Categoria> readById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/id/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNome(categoriaAtualizada.getNome());
            categoria.setDescricao(categoriaAtualizada.getDescricao());
            Categoria updated = categoriaRepository.save(categoria);
            return ResponseEntity.ok(updated);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/id/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            categoriaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

