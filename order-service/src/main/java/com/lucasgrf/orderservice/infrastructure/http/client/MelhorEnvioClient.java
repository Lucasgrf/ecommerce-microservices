package com.lucasgrf.orderservice.infrastructure.http.client;

import com.lucasgrf.orderservice.infrastructure.http.client.dto.MelhorEnvioCalculateRequest;
import com.lucasgrf.orderservice.infrastructure.http.client.dto.MelhorEnvioCalculateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "melhor-envio", url = "${melhor-envio.url:https://www.melhorenvio.com.br}")
public interface MelhorEnvioClient {

    @PostMapping("/api/v2/me/shipment/calculate")
    List<MelhorEnvioCalculateResponse> calculateShipping(
            @RequestHeader("Authorization") String token,
            @RequestBody MelhorEnvioCalculateRequest request
    );
}
