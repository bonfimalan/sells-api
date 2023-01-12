package io.github.bonfimalan.sells.conversion;

import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.domain.OrderProduct;
import io.github.bonfimalan.sells.domain.Product;
import io.github.bonfimalan.sells.dto.OrderProductDTO;

public class OrderProductConverter {
    public static OrderProduct from(OrderProductDTO dto, Order order, Product product) {
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .amount(dto.getAmount())
                .build();
    }
}
