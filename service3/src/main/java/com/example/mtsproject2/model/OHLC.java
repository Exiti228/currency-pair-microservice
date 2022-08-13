package com.example.mtsproject2.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OHLC {
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private LocalDateTime start;
    private LocalDateTime end;
    private String pairName;

    public OHLC(Double open, Double high, Double low, Double close, LocalDateTime start, LocalDateTime end, String pairName) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.start = start;
        this.end = end;
        this.pairName = pairName;
    }
}
