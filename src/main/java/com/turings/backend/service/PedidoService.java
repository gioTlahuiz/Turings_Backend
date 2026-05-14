package com.turings.backend.service;

import com.turings.backend.model.Pedido;
import com.turings.backend.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido findById(Integer id_pedido) {
        return pedidoRepository.findById(id_pedido).orElse(null);
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // Actualizar pedido existente
    @Transactional
    public Pedido update(Integer id_pedido, Pedido pedidoActualizado) {
        return pedidoRepository.findById(id_pedido)
                .map(pedido -> {
                    pedido.setUsuario(pedidoActualizado.getUsuario());
                    pedido.setFecha_pedido(pedidoActualizado.getFecha_pedido());
                    pedido.setDireccion(pedidoActualizado.getDireccion());
                    pedido.setRastreador(pedidoActualizado.getRastreador());
                    pedido.setDetallesPedidos(pedidoActualizado.getDetallesPedidos());
                    pedido.setReview(pedidoActualizado.getReview());
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id_pedido));
    }

    // Eliminar pedido
    @Transactional
    public void delete(Integer id_pedido) {
        if (!pedidoRepository.existsById(id_pedido)) {
            throw new RuntimeException("Pedido no encontrado con ID: " + id_pedido);
        }
        pedidoRepository.deleteById(id_pedido);
    }
}