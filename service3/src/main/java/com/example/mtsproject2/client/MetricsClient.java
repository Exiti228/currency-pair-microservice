package com.example.mtsproject2.client;

import com.example.mtsproject2.model.OHLC;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;


@FeignClient(name = "metrics-client", url= "${metrics.client.url}")
public interface MetricsClient {
    @GetMapping(value = "/metrics", produces = MediaType.APPLICATION_JSON_VALUE)
    List<OHLC> getMetrics(@RequestParam(name = "from") LocalDateTime from,
                          @RequestParam(name = "to") LocalDateTime to);
}
