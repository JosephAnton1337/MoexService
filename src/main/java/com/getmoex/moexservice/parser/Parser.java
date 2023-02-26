package com.getmoex.moexservice.parser;

import com.getmoex.moexservice.dto.BondDto;

import java.util.List;

public interface Parser {
    List<BondDto> parse(String rates);
}
