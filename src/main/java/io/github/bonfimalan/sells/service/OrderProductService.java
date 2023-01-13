package io.github.bonfimalan.sells.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.bonfimalan.sells.domain.OrderProduct;
import io.github.bonfimalan.sells.exception.NotFoundException;
import io.github.bonfimalan.sells.repository.OrderProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderProductService {
    private final OrderProductRepository repository;

    public OrderProduct getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No OrderProduct with id " + id));
    }

    public List<OrderProduct> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId);
    }
}
