package com.turings.backend.controller;

import com.turings.backend.model.DetallesPedidos;
import com.turings.backend.service.DetallesPedidosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/***
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("/api/v1/detalles-pedidos")
@CrossOrigin(origins = "*") //Permite resolver los conflictos de los CORS
public class DetallesPedidosController {

    final private DetallesPedidosService detallesPedidosService;

    public DetallesPedidosController(DetallesPedidosService detallesPedidosService) {
        this.detallesPedidosService = detallesPedidosService;
    }

    /***
     * Metodo GET para visualizar todos los pedidos.
     * ResponseEntity -> Es el JSON
     * @return una lista (arreglo) con los objetos productos en estructura JSON
     */
    @GetMapping
    public ResponseEntity<List<DetallesPedidos>> list() {
        return ResponseEntity.ok(detallesPedidosService.findAll());
    }

    /***
     * Metodo GET para visualizar un pedido por ID
     * @param id ID del detalle del producto
     * @return Se verifica si existe el detalle del producto en caso de que no retorna un mensaje 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetallesPedidos> details(@PathVariable Long id) {
        Optional<DetallesPedidos> optionalDetallesPedidos = detallesPedidosService.findById(id);
        if (optionalDetallesPedidos.isPresent()) {
            return ResponseEntity.ok(optionalDetallesPedidos.get());
        }
        return ResponseEntity.notFound().build();
    }

    /***
     * Metodo POST para crear un pedido
     * @RequestBody es donde se envia en formato JSON el objeto de DetallesPedidos
     * @param detallesPedidos Nuevo detalle de producto a crear
     * @return Regresa un mensaje 200, ya que el detalle del producto se creó en la tabla
     */
    @PostMapping
    public ResponseEntity<DetallesPedidos> createProduct(@RequestBody DetallesPedidos detallesPedidos) {
        DetallesPedidos detallesPedidosDB = detallesPedidosService.saveDetails(detallesPedidos);
        return ResponseEntity.status(HttpStatus.CREATED).body(detallesPedidosDB);
    }

    /***
     * Metodo PUT para editar un pedido
     * @param detallesPedidos Es para crear un nuevo objeto en la DB
     * @param id Con el ID se verifica que exista el detalle del producto que se busca editar
     * @return En caso que exista el detalle del producto con id se guarda, en caso que no generara un mensaje 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<DetallesPedidos> update(@RequestBody DetallesPedidos detallesPedidos, @PathVariable Long id) {
        //Primero buscamos en base de datos que exista el objeto
        Optional<DetallesPedidos> optionalDetallesPedidos = detallesPedidosService.findById(id);
        if (optionalDetallesPedidos.isPresent()) {
            DetallesPedidos detallesPedidosDB = optionalDetallesPedidos.get(); //se puede usar .get() o .orElseThrow()

            detallesPedidosDB.setPedido(detallesPedidos.getPedido());
            detallesPedidosDB.setId_detalle(detallesPedidos.getId_detalle());
            detallesPedidosDB.setCantidad_producto(detallesPedidos.getCantidad_producto());
            detallesPedidosDB.setPrecio_total(detallesPedidos.getPrecio_total());
            detallesPedidosDB.setRastreador(detallesPedidos.getRastreador());
            detallesPedidosDB.setImagen(detallesPedidos.getImagen());
            detallesPedidosDB.setEstado_pedido(detallesPedidos.getEstado_pedido());
            detallesPedidosDB.setProducto(detallesPedidos.getProducto());

            //Lo que hace es pasar el status de la petición
            return ResponseEntity.status(HttpStatus.CREATED).body(detallesPedidosService.saveDetails(detallesPedidosDB));
        }
        return ResponseEntity.notFound().build();
    }

    /***
     * Metodo DELETE para eliminar un detalle de pedido
     * @param id Para verificar que exista el detalle del producto por ID
     * @return Regresa un mensaje 200 si se elimino, sino un 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<DetallesPedidos> delete(@PathVariable Long id) {
        Optional<DetallesPedidos> optionalDetallesPedidos = detallesPedidosService.deleteById(id);
        if (optionalDetallesPedidos.isPresent()) {
            DetallesPedidos detallesPedidosDelete = optionalDetallesPedidos.orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(detallesPedidosDelete);
        }
        return ResponseEntity.notFound().build();
    }

}
