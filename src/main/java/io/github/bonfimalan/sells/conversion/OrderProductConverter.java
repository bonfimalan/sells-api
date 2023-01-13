package io.github.bonfimalan.sells.conversion;

import java.util.List;
import java.util.stream.Collectors;

import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.domain.OrderProduct;
import io.github.bonfimalan.sells.domain.Product;
import io.github.bonfimalan.sells.dto.InfoOrderProductDTO;
import io.github.bonfimalan.sells.dto.OrderProductDTO;

public class OrderProductConverter {
    public static OrderProduct from(OrderProductDTO dto, Order order, Product product) {
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .amount(dto.getAmount())
                .build();
    }

    public static List<InfoOrderProductDTO> from(List<OrderProduct> list) {
        return list.stream()
                .map(orderProduct -> InfoOrderProductDTO.builder()
                            .amount(orderProduct.getAmount())
                            .price(orderProduct.getProduct().getPrice())
                            .description(orderProduct.getProduct().getDescription())
                            .build()
                )
                .collect(Collectors.toList());
    }
}
