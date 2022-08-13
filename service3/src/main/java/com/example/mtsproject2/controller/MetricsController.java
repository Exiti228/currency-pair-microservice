package com.example.mtsproject2.controller;

import com.example.mtsproject2.model.OHLC;
import com.example.mtsproject2.repository.OHLCRepository;
import com.example.mtsproject2.service.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/metrics")
    public List<OHLC> getMetrics(@RequestParam(name = "from")LocalDateTime from,
                                 @RequestParam(name = "to") LocalDateTime to) {
        return metricsService.getMetrics(from, to);
    }
    @GetMapping("/ohlc")
    public List<OHLC> getOHLC(@RequestParam(name = "value") String value) {
        return metricsService.getOHLC(value);
    }
    @GetMapping("/ohlc/all")
    public List<OHLC> allOHLC() {
        return OHLCRepository.ohlcs;
    }
}
