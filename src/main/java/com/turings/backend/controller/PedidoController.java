package com.turings.backend.controller;

import com.turings.backend.model.Pedido;
import com.turings.backend.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos") // Mantenemos el estándar de versionado
@CrossOrigin(origins = "*") // Permite que tu portafolio web se conecte sin bloqueos
public class PedidoController {

    // 1. Definimos el servicio como final (inmutable)
    private final PedidoService pedidoService;

    // 2. Constructor para la inyección de dependencias
    // Nota: En versiones modernas de Spring, si solo hay un constructor, no es necesario poner @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Obtener todos los pedidos
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }

    // Obtener pedido por ID
    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable("id_pedido") int id) {
        return pedidoService.findById(id);
    }

    // Crear nuevo pedido
    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoService.save(pedido);
    }

    // Actualizar pedido existente
    @PutMapping("/{id}")
    public Pedido updatePedido(@PathVariable("id_pedido") int id, @RequestBody Pedido pedido) {
        // Aseguramos que el objeto tenga el ID correcto antes de mandarlo al service
        pedido.setId_pedido(id);
        return pedidoService.update(id, pedido);
    }

    // Eliminar pedido
    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable("id_pedido") int id) {
        pedidoService.delete(id);
    }
}