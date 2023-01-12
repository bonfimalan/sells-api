package io.github.bonfimalan.sells.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.domain.OrderStatus;
import io.github.bonfimalan.sells.dto.OrderDTO;
import io.github.bonfimalan.sells.dto.OrderStatusDTO;
import io.github.bonfimalan.sells.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody OrderDTO orderDTO) {
        Order order = service.save(orderDTO);
        return order.getId();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable Integer id, @RequestBody OrderStatusDTO statusDTO) {
        OrderStatus orderStatus = OrderStatus.valueOf(statusDTO.getStatus().toUpperCase()); 
        service.updateStatus(id, orderStatus);
    }
}
