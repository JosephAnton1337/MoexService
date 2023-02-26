package com.getmoex.moexservice.dto;

import com.getmoex.moexservice.model.Stock;
import lombok.Value;

import java.util.List;

@Value
public class StocksDto {
    List<Stock> list;
}
