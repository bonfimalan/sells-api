package io.github.bonfimalan.sells.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDTO {
    private Integer client;
    private BigDecimal total;
    private List<OrderProductDTO> items;
}
