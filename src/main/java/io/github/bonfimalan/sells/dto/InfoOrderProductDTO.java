package io.github.bonfimalan.sells.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InfoOrderProductDTO {
    private String description;
    private BigDecimal price;
    private Integer amount;
}
