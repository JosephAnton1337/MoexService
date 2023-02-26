package com.getmoex.moexservice.controller;

import com.getmoex.moexservice.dto.FigiesRequest;
import com.getmoex.moexservice.dto.StockPricesResponse;
import com.getmoex.moexservice.dto.StocksDto;
import com.getmoex.moexservice.dto.TicketDto;
import com.getmoex.moexservice.service.BondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bonds")
@Slf4j
public class MoexBindController {

    private final BondService bondService;

    @PostMapping("getBondsByTickers")
    public StocksDto getBondsFromMoex(@RequestBody TicketDto ticketDto) {
        return bondService.getBondsFromMoex(ticketDto);
    }

    @PostMapping("getBondsByTickers")
    public StockPricesResponse getPricesByFigies(@RequestBody FigiesRequest figiesRequest) {
        return bondService.getPricesByFigies(figiesRequest);
    }
}
