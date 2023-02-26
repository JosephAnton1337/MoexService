package com.getmoex.moexservice.service;

import com.getmoex.moexservice.dto.BondDto;
import com.getmoex.moexservice.dto.FigiesRequest;
import com.getmoex.moexservice.dto.StockPrice;
import com.getmoex.moexservice.dto.StockPricesResponse;
import com.getmoex.moexservice.dto.StocksDto;
import com.getmoex.moexservice.dto.TicketDto;
import com.getmoex.moexservice.exception.BondNotFoundException;
import com.getmoex.moexservice.model.Currency;
import com.getmoex.moexservice.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BondService {
    private final BondRepository bondRepository;
    private final CacheManager cacheManager;


    public StocksDto getBondsFromMoex(TicketDto ticketDto) {

        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getGovernmentBonds());
        allBonds.addAll(bondRepository.getCorporateBonds());

        List<BondDto> resultBonds = allBonds.stream().
                filter(it -> ticketDto.getTickers().contains(it.getTicker())).collect(Collectors.toList());

        List<Stock> stocks = resultBonds.stream().map(it -> Stock.builder()
                        .ticker(it.getTicker())
                        .name(it.getName())
                        .figi(it.getTicker())
                        .currency(Currency.RUB)
                        .source("MOEX")
                        .build())
                .collect(Collectors.toList());
        return new StocksDto(stocks);


    }

    public StockPricesResponse getPricesByFigies(FigiesRequest figiesRequest) {

        log.info("Request for figies {}", figiesRequest.getFigies());
        List<String> figies = new ArrayList<>(figiesRequest.getFigies());

        List<BondDto> allBonds = new ArrayList<>();
        allBonds.addAll(bondRepository.getGovernmentBonds());
        allBonds.addAll(bondRepository.getCorporateBonds());
        figies.removeAll(allBonds.stream().map(it -> it.getTicker()).collect(Collectors.toList()));
        if (!figies.isEmpty()) {
            throw new BondNotFoundException(String.format("Bonds %s not found.", figies));
        }
        List<StockPrice> prices = allBonds.stream().filter(it -> figiesRequest.getFigies().contains(it.getTicker()))
                .map(it -> new StockPrice(it.getTicker(), it.getPrice() * 10))
                .collect(Collectors.toList());

        return new StockPricesResponse(prices);
    }
}
