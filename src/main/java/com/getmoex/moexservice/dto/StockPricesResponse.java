package com.getmoex.moexservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
@Data
public class StockPricesResponse {
    private List<StockPrice> prices;
}
