package com.imagehub.payment_management.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderRepository orderRepository;


    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("")
    List<Order> getAll(){
        return orderRepository.getAll();
    }

    @GetMapping("/{order_id}")
    Order getById(@PathVariable Integer order_id) {
        return orderRepository.getById(order_id).orElseThrow();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Order order){
        orderRepository.create(order);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody Order order, @PathVariable Integer order_id){
        orderRepository.update(order, order_id);
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete (@PathVariable Integer order_id){
        orderRepository.delete(order_id);
    }

}
