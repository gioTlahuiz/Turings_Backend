package com.turings.backend.service;

import com.turings.backend.model.DetallesPedidos;
import com.turings.backend.model.Pedido;
import com.turings.backend.model.Producto;
import com.turings.backend.repository.DetallesPedidosRepository;
import com.turings.backend.repository.PedidoRepository;
import com.turings.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/***
 * @version 1.0
 * @since 1.0
 */
@Service
public class DetallesPedidosService {

//    final private DetallesPedidosRepository detallesPedidosRepository;

    @Autowired
    private DetallesPedidosRepository detallesPedidosRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    //Metodo para encontrar todo los detalles de producto
    public List<DetallesPedidos> findAll() {
        return detallesPedidosRepository.findAll();
    }

    //Metodo para encontrar detalle de producto por ID
    public Optional<DetallesPedidos> findById(Long id) {
        return detallesPedidosRepository.findById(id);
    }

    //Metodo para guardar detalles de productos
    public DetallesPedidos saveDetails(DetallesPedidos detallesPedidos) {
        // getReferenceById obtiene el "proxy" del objeto directamente
        Pedido pedidoExistente = pedidoRepository.getReferenceById((long) detallesPedidos.getProducto().getId_producto());   //
        Producto productoExistente = productoRepository.getReferenceById((long) detallesPedidos.getProducto().getId_producto());

        detallesPedidos.setPedido(pedidoExistente);
        detallesPedidos.setProducto(productoExistente);

        return detallesPedidosRepository.save(detallesPedidos);
    }

    //Metodo para eliminar detalles de productos por ID
    public Optional<DetallesPedidos> deleteById(Long id) {
        Optional<DetallesPedidos> detallesPedidosOptional = detallesPedidosRepository.findById(id);
        if (detallesPedidosOptional.isPresent()) {
            detallesPedidosRepository.deleteById(id);
            return detallesPedidosOptional;
        }
        return Optional.empty();
    }
}
