package com.getmoex.moexservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "corporatebonds", url = "${moex.bonds.corporate.url}",configuration = FeingConfig.class)
public interface CorporateBondsClient {

    @GetMapping
    String getBondsFromMoex();
}
