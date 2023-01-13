package io.github.bonfimalan.sells.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.bonfimalan.sells.conversion.OrderConverter;
import io.github.bonfimalan.sells.conversion.OrderProductConverter;
import io.github.bonfimalan.sells.domain.Client;
import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.domain.OrderProduct;
import io.github.bonfimalan.sells.domain.OrderStatus;
import io.github.bonfimalan.sells.dto.OrderDTO;
import io.github.bonfimalan.sells.dto.OrderProductDTO;
import io.github.bonfimalan.sells.exception.NotFoundException;
import io.github.bonfimalan.sells.exception.ServiceException;
import io.github.bonfimalan.sells.repository.OrderProductRepository;
import io.github.bonfimalan.sells.repository.OrderRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final ClientService clientService;
    private final ProductService productService;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Order save(OrderDTO orderDTO) {
        Client client;
        try {
            client = clientService.getClientById(orderDTO.getClient());
        } catch (NotFoundException exception) {
            throw new ServiceException("Invalid client id");
        }
        
        Order order = OrderConverter.from(orderDTO, client);

        List<OrderProduct> items;
        try{
            items = convertItems(order, orderDTO.getItems());
        } catch(NotFoundException exception) {
            throw new ServiceException("Invalid product Id");
        }
        repository.save(order);
        orderProductRepository.saveAll(items);
        return order;
    }

    private List<OrderProduct> convertItems(Order order, List<OrderProductDTO> items) {
        if (items.isEmpty()) {
            throw new ServiceException("Can't make a order without items");
        }

        return items
                .stream()
                .map(dto -> {
                    return OrderProductConverter.from(dto, order, productService.getById(dto.getProduct()));
                })
                .collect(Collectors.toList());
    }

    public Order getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No order with id " + id));
    }

    public void updateStatus(Integer id, OrderStatus status) {
        Order orderToUpdateStatus = null;
        try {
            orderToUpdateStatus = getById(id);
        } catch (NotFoundException ex) {
            throw new ServiceException("No order with id " + id);
        }

        orderToUpdateStatus.setStatus(status);
        repository.save(orderToUpdateStatus);
    }
}
