package com.Games.LojaGames.controller;

import com.Games.LojaGames.model.ItemPedido;
import com.Games.LojaGames.model.Pedido;
import com.Games.LojaGames.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                item.setPedido(pedido);
            }
        }

        Pedido novoPedido = pedidoRepository.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoRecebido) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);

        if (pedidoOpt.isPresent()) {
            Pedido pedidoExistente = pedidoOpt.get();

            pedidoExistente.setStatus(pedidoRecebido.getStatus());
            pedidoExistente.setMetodoPagamento(pedidoRecebido.getMetodoPagamento());
            pedidoExistente.setEnderecoEntrega(pedidoRecebido.getEnderecoEntrega());
            pedidoExistente.setValorTotal(pedidoRecebido.getValorTotal());

            Pedido pedidoSalvo = pedidoRepository.save(pedidoExistente);
            return ResponseEntity.ok(pedidoSalvo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}