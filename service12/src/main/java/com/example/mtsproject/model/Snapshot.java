package com.example.mtsproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "snapshot")
@Data
public class Snapshot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(name = "price")
    private Double price;

    public Snapshot(Double price, LocalDateTime date, CurrencyPair currencyPair) {
        this.price = price;
        this.date = date;
        this.currencyPair = currencyPair;
    }

    @Column(name = "date")
    LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id", referencedColumnName = "id")
    @JsonIgnore
    private CurrencyPair currencyPair;

    public Snapshot() {

    }
}
