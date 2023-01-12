package io.github.bonfimalan.sells.service;

import java.util.Optional;

import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.rest.dto.OrderDTO;

public interface OrderService {
    Order save(OrderDTO orderDTO);
    Optional<Order> getOrderById(Integer id);
}
