package io.github.bonfimalan.sells.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InfoOrderDTO {
    private Integer id;
    private String clientName;
    private BigDecimal total;
    private List<InfoOrderProductDTO> items;
} 
