package com.getmoex.moexservice.service;

import com.getmoex.moexservice.client.CorporateBondsClient;
import com.getmoex.moexservice.client.GovernmentBondsClient;
import com.getmoex.moexservice.dto.BondDto;
import com.getmoex.moexservice.exception.LimitRequestsException;
import com.getmoex.moexservice.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BondRepository {

    private final CorporateBondsClient corporateBondsClient;
    private final GovernmentBondsClient governmentBondsClient;
    private final Parser parser;
    private final CacheManager cacheManager;

    @Cacheable(value = "corps")
    public List<BondDto> getCorporateBonds() {
        log.info("Getting corporate bonds from Moex");
        String xmlFromMoex = corporateBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds");
            throw new LimitRequestsException("Moex isn't answering for getting corporate bonds.");
        } else {
            return bondDtos;
        }
    }

    @Cacheable(value = "govs")
    public List<BondDto> getGovernmentBonds() {
        log.info("Getting government bonds from Moex");
        String xmlFromMoex = governmentBondsClient.getBondsFromMoex();
        List<BondDto> bondDtos = parser.parse(xmlFromMoex);
        if (bondDtos.isEmpty()) {
            log.error("Moex isn't answering for getting corporate bonds");
            throw new LimitRequestsException("Moex isn't answering for getting corporate bonds.");
        } else {
            return bondDtos;
        }

    }
}
