package com.getmoex.moexservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
public class BondDto {
    String ticker;
    String name;
    Double price;


}
