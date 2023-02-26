package com.getmoex.moexservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "governmentbonds", url = "${moex.bonds.government.url}", configuration = FeingConfig.class)
public interface GovernmentBondsClient {
    @GetMapping
    String getBondsFromMoex();

}
