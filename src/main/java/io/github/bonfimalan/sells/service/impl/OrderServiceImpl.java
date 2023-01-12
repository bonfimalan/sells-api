package io.github.bonfimalan.sells.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.bonfimalan.sells.domain.Client;
import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.domain.OrderProduct;
import io.github.bonfimalan.sells.domain.Product;
import io.github.bonfimalan.sells.domain.repository.ClientRepository;
import io.github.bonfimalan.sells.domain.repository.OrderProductRepository;
import io.github.bonfimalan.sells.domain.repository.OrderRepository;
import io.github.bonfimalan.sells.domain.repository.ProductRepository;
import io.github.bonfimalan.sells.exception.ServiceException;
import io.github.bonfimalan.sells.rest.dto.OrderDTO;
import io.github.bonfimalan.sells.rest.dto.OrderProductDTO;
import io.github.bonfimalan.sells.service.OrderService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    @Transactional
    public Order save(OrderDTO orderDTO) {
        Integer idClient = orderDTO.getClient();
        Client client = clientRepository
                .findById(idClient)
                .orElseThrow(() -> new ServiceException("Invalid client id"));

        Order order = new Order();
        order.setTotal(orderDTO.getTotal());
        order.setDateOrder(LocalDate.now());
        order.setClient(client);

        List<OrderProduct> items = convertItems(order, orderDTO.getItems());
        
        repository.save(order);
        orderProductRepository.saveAll(items);

        return order;
    }

    private List<OrderProduct> convertItems(Order order,  List<OrderProductDTO> items) {
        if(items.isEmpty()) {
            throw new ServiceException("Can't make a order without items");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduct = dto.getProduct();
                    Product product = productRepository
                            .findById(idProduct)
                            .orElseThrow(() -> new ServiceException("Product id " + idProduct + " doesn't exists"));

                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(product);
                    orderProduct.setAmount(dto.getAmount());

                    return orderProduct;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        
        return null;
    }
}
