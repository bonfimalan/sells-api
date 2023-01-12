package io.github.bonfimalan.sells.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.bonfimalan.sells.domain.Client;
import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.domain.OrderProduct;
import io.github.bonfimalan.sells.domain.OrderStatus;
import io.github.bonfimalan.sells.domain.Product;
import io.github.bonfimalan.sells.dto.OrderDTO;
import io.github.bonfimalan.sells.dto.OrderProductDTO;
import io.github.bonfimalan.sells.exception.NotFoundException;
import io.github.bonfimalan.sells.exception.ServiceException;
import io.github.bonfimalan.sells.repository.ClientRepository;
import io.github.bonfimalan.sells.repository.OrderProductRepository;
import io.github.bonfimalan.sells.repository.OrderRepository;
import io.github.bonfimalan.sells.repository.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

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
        order.setStatus(OrderStatus.IN_PROGRESS);

        List<OrderProduct> items = convertItems(order, orderDTO.getItems());

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

    public Order getById(Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No order with id " + id));
    }

    public Order getByIdWithItems(Integer id) {
        // TODO implement this
        return null;
    }

    public void updateStatus(Integer id, OrderStatus status) {
        Order orderToUpdateStatus = null;
        try{
            orderToUpdateStatus = getById(id);
        } catch(NotFoundException ex) { throw new ServiceException("No order with id " + id );}

        orderToUpdateStatus.setStatus(status);
        repository.save(orderToUpdateStatus);
    }
}
