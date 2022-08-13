package com.example.mtsproject2.model;




import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Snapshot {
    private Double price;
    private LocalDateTime date;

    public Snapshot(Double value, LocalDateTime localDateTime) {
        this.price = value;
        this.date = localDateTime;
    }
}
