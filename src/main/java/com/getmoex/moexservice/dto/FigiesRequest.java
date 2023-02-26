package com.getmoex.moexservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FigiesRequest {
    private List<String> figies;
}
