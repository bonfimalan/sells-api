package io.github.bonfimalan.sells.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderProductDTO {
    private Integer product;
    private Integer amount;
}
