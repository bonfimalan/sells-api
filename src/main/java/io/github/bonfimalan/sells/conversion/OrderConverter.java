package io.github.bonfimalan.sells.conversion;

import java.time.LocalDate;

import static io.github.bonfimalan.sells.domain.OrderStatus.IN_PROGRESS;

import io.github.bonfimalan.sells.domain.Client;
import io.github.bonfimalan.sells.domain.Order;
import io.github.bonfimalan.sells.dto.OrderDTO;

public class OrderConverter {
    public static Order from(OrderDTO dto, Client client) {
        return Order.builder()
                .client(client)
                .total(dto.getTotal())
                .status(IN_PROGRESS)
                .dateOrder(LocalDate.now())
                .build();
    }
}
