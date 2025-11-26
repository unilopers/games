package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.ItemPedido;
import com.Games.LojaGames.repository.ItemPedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoController(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<ItemPedido> listarTodos() {
        return itemPedidoRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ItemPedido> buscarPorId(@PathVariable Long id) {
        return itemPedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ItemPedido> criarItem(@RequestBody ItemPedido itemPedido) {
        ItemPedido novoItem = itemPedidoRepository.save(itemPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ItemPedido> atualizarItem(@PathVariable Long id, @RequestBody ItemPedido itemRecebido) {
        return itemPedidoRepository.findById(id)
                .map(item -> {
                    item.setQuantidade(itemRecebido.getQuantidade());
                    item.setPrecoUnitario(itemRecebido.getPrecoUnitario());
                    item.setSubtotal(itemRecebido.getSubtotal());
                    if(itemRecebido.getJogo() != null) item.setJogo(itemRecebido.getJogo());

                    return ResponseEntity.ok(itemPedidoRepository.save(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        if (itemPedidoRepository.existsById(id)) {
            itemPedidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}