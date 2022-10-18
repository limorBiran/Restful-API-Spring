package Limor.Almog.restApplication.controllers;

import Limor.Almog.restApplication.repositories.OrderTvShowRepo;
import Limor.Almog.restApplication.assemblers.OrderTvShowEntityAssembler;
import Limor.Almog.restApplication.assemblers.OrderTvShowsDTOAssembler;
import Limor.Almog.restApplication.dto.OrderTvShowsDTO;
import Limor.Almog.restApplication.entities.OrderTvShows;
import Limor.Almog.restApplication.exceptions.OrderNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class OrderTvShowController {
    private OrderTvShowRepo orderTvShowRepo;
    private OrderTvShowEntityAssembler orderTvShowEntityAssembler;
    private OrderTvShowsDTOAssembler orderTvShowsDTOAssembler;

    public OrderTvShowController(OrderTvShowRepo orderTvShowRepo, OrderTvShowEntityAssembler orderTvShowEntityAssembler, OrderTvShowsDTOAssembler orderTvShowsDTOAssembler) {
        this.orderTvShowRepo = orderTvShowRepo;
        this.orderTvShowEntityAssembler = orderTvShowEntityAssembler;
        this.orderTvShowsDTOAssembler = orderTvShowsDTOAssembler;
    }


    /**
     * Get method - find the all orders in the DB.
     * @return List<OrderTvShows>
     */
    @GetMapping("/orders")
    @Operation(summary = "get all orders")
    public ResponseEntity<CollectionModel<EntityModel<OrderTvShows>>> getAllOrders()
    {
        List<OrderTvShows> ordersList = (List<OrderTvShows>) orderTvShowRepo.findAll();
        List<EntityModel<OrderTvShows>> entityModelList = ordersList.stream().map(order->EntityModel.of(order)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withSelfRel()));
    }

    /**
     * Get method - find order Tv Show by id
     * @param id
     * @return OrderTvShows
     */
    @GetMapping("/orders/{id}")
    @Operation(summary = "get order by id")
    public ResponseEntity<EntityModel<OrderTvShows>> getOrderById(@PathVariable long id) {
        OrderTvShows orderTvShows = orderTvShowRepo.findById(id).orElseThrow(()->new OrderNotFoundException(id));
        return ResponseEntity.ok(EntityModel.of(orderTvShows,
                linkTo(methodOn(OrderTvShowController.class).getOrderById(id)).withSelfRel(),
                linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withRel("back to all orders")));
    }

    /**
     * Get method - find orders Tv Show by price
     * @param price
     * @return List<OrderTvShows>
     */
    @GetMapping("/orders/price/{price}")
    @Operation(summary = "get orders by price")
    public ResponseEntity<CollectionModel<EntityModel<OrderTvShows>>> getOrdersByPrice(@PathVariable Double price) {
        List<OrderTvShows> ordersTvShowsList = orderTvShowRepo.findByPrice(price);
        List<EntityModel<OrderTvShows>> entityModelList = ordersTvShowsList.stream().map(order->EntityModel.of(order)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(OrderTvShowController.class).getOrdersByPrice(price)).withSelfRel(),
                linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withRel("back to all orders")));
    }

    /**
     * Get method - find orders Tv Show by order date
     * @param orderDate
     * @return List<OrderTvShows>
     */
    @GetMapping("/orders/orderDate/{orderDate}")
    @Operation(summary = "get orders by order date")
    public ResponseEntity<CollectionModel<EntityModel<OrderTvShows>>> getOrdersByOrderDate(@PathVariable String orderDate) {
        List<OrderTvShows> ordersTvShowsList = orderTvShowRepo.findByOrderDate(orderDate);
        List<EntityModel<OrderTvShows>> entityModelList = ordersTvShowsList.stream().map(order->EntityModel.of(order)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(OrderTvShowController.class).getOrdersByOrderDate(orderDate)).withSelfRel(),
                linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withRel("back to all orders")));
    }

    /**
     * Get method - find orders Tv Show by count of product
     * @param count
     * @return List<OrderTvShows>
     */
    @GetMapping("/orders/countOfProducts/{count}")
    @Operation(summary = "get orders by count of products")
    public ResponseEntity<CollectionModel<EntityModel<OrderTvShows>>> getOrdersByCountOfProducts(@PathVariable int count) {
        List<OrderTvShows> ordersTvShowsList = orderTvShowRepo.findByCountOfProducts(count);
        List<EntityModel<OrderTvShows>> entityModelList = ordersTvShowsList.stream().map(order->EntityModel.of(order)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(OrderTvShowController.class).getOrdersByCountOfProducts(count)).withSelfRel(),
                linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withRel("back to all orders")));
    }

    /**
     * Get method - get all orders Tv Show' information
     * @return List<OrderTvShowsDTO>
     */
    @GetMapping("/orders/info")
    @Operation(summary = "get all orders' information")
    public ResponseEntity<CollectionModel<EntityModel<OrderTvShowsDTO>>> getAllOrdersInfo() {
        return ResponseEntity.ok(
                orderTvShowsDTOAssembler.toCollectionModel(
                        StreamSupport.stream(orderTvShowRepo.findAll().spliterator(),
                                        false)
                                .map(OrderTvShowsDTO::new)
                                .collect(Collectors.toList())));
    }

    /**
     * Get method - get orders Tv Show' information by id
     * @param id
     * @return OrderTvShowsDTO
     */
    @GetMapping("/orders/{id}/info")
    @Operation(summary = "get orders' information by id")
    public ResponseEntity<EntityModel<OrderTvShowsDTO>> getOrderInfoById(@PathVariable Long id) {
        return orderTvShowRepo.findById(id)
                .map(OrderTvShowsDTO::new)
                .map(orderTvShowsDTOAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get method - get orders Tv Show by price and order date
     * @param price
     * @param orderDate
     * @return List<OrderTvShows>
     */
    @GetMapping("/orders/orderDetails")
    @Operation(summary = "get orders by price and order date")
    public ResponseEntity<CollectionModel<EntityModel<OrderTvShows>>> getOrdersByPriceAndDate(@RequestParam Double price, @RequestParam String orderDate) {
        List<OrderTvShows> ordersTvShowsList = orderTvShowRepo.findByPriceAndOrderDate(price, orderDate);
        List<EntityModel<OrderTvShows>> entityModelList = ordersTvShowsList.stream().map(order->EntityModel.of(order)).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(entityModelList,
                linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withSelfRel()));
    }

    /**
     * Get method - get all orders that greater of price and contain a maximum of x count tv shows in order.
     * @param minPrice
     * @param maxCountOfTvShowsInOrder
     * @return List<OrderTvShows>
     */
    @GetMapping("/orders/bestOrders")
    @Operation(summary = "Get all orders that greater of price and contain a maximum of x count tv shows in order.")
    public ResponseEntity<CollectionModel<EntityModel<OrderTvShows>>> getBestOrders(@RequestParam Double minPrice, @RequestParam int maxCountOfTvShowsInOrder) {
        List<EntityModel<OrderTvShows>> orders = StreamSupport.stream(orderTvShowRepo.findAll().spliterator(), false)
                .filter(order -> (order.getPrice() >= minPrice & order.getCountOfProducts() <= maxCountOfTvShowsInOrder))
                .map(orderTvShowEntityAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(orders,
                linkTo(methodOn(OrderTvShowController.class).getAllOrders()).withSelfRel()));
    }

    /**
     * Delete method - delete order tv show by id.
     * @param id
     */
    @DeleteMapping("/orders/{id}")
    @Operation(summary = "delete order by id")
    void deleteOrderById(@PathVariable Long id) {
        orderTvShowRepo.deleteById(id);
    }
}
